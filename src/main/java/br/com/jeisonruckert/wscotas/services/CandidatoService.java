package br.com.jeisonruckert.wscotas.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import br.com.jeisonruckert.wscotas.entities.Candidato;
import br.com.jeisonruckert.wscotas.entities.Cota;
import br.com.jeisonruckert.wscotas.entities.Curso;
import br.com.jeisonruckert.wscotas.entities.DTO.CandidatoDTO;
import br.com.jeisonruckert.wscotas.repositories.CandidatoRepository;
import br.com.jeisonruckert.wscotas.services.exceptions.FileReaderException;
import br.com.jeisonruckert.wscotas.services.exceptions.InvalidNumberOfCourseVacancyException;

@Service
public class CandidatoService {

	static final String[] ordemDeCodigoDasCotas = { "C1", "C10", "C11", "C9", "C8", "C7", "C5", "C6", "C4", "C3",
			"C2" };
	static final Double[] percentuaisParaObtencaoDaQuantidadeDeVagasDeCadaCota = { .4, .05, .05, .16016, .04784, .03234,
			.16016, .00966, .04784, .03234, .00966 };
	Integer[] vagasPorCota = new Integer[11];

	private final CandidatoRepository repo;

	private final CotaService cotaService;
	
	private final CursoService cursoService;

	public CandidatoService(CandidatoRepository repository, CotaService cotaService, CursoService cursoService) {
		this.repo = repository;
		this.cotaService = cotaService;
		this.cursoService = cursoService;
	}

	public Curso insertMainFile(byte[] csvFile) {
		List<CandidatoDTO> candidatosDTO = new ArrayList<>();
		Scanner sc = null;
		Curso curso = null;

		try {
			File file = File.createTempFile("csvFile", ".tmp");
			FileOutputStream in = new FileOutputStream(file);
			in.write(csvFile);
			in.close();

			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String[] line = sc.nextLine().split(",");

				@Valid
				CandidatoDTO candidatoDTO = new CandidatoDTO(line[5], line[4], line[2], line[1], line[3],
						Integer.parseInt(line[6]), Double.parseDouble(line[12]), Integer.parseInt(line[13]),
						Integer.parseInt(line[26]));
				candidatosDTO.add(candidatoDTO);
			}

			vagasPorCota = obterQuantidadeDeVagasParaCadaCotaDoCurso(candidatosDTO.get(0).getVagasDoCurso());

			curso = new Curso(candidatosDTO.get(0).getCurso());
			for (int i = 0; i <= 10; i++) {
				Cota cota = new Cota(ordemDeCodigoDasCotas[i], i, vagasPorCota[i], curso);
				cotaService.save(cota);
				curso.getCotas().add(cota);
			}

			for (CandidatoDTO candDTO : candidatosDTO) {
				Candidato candidato = createCandidato(candDTO, curso);
				repo.save(candidato);
			}
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException | IllegalStateException | IOException e) {
			throw new FileReaderException("Erro na leitura de arquivo");
		} finally {
			if (sc != null) {
				sc.close();
			}
		}

		return curso;
	}

	public List<Candidato> generateList(Integer cursoId, Integer chamadaId) {
		cursoService.findById(cursoId);
		List<Candidato> listaDeChamada = new ArrayList<>();
		List<Candidato> candidatos = repo.findByCursoId(cursoId);
		System.out.println(candidatos);
		System.out.println(chamadaId);
		System.out.println("---------------------------------------------");
		Curso curso = candidatos.get(0).getCurso();

		for (Cota cota : curso.getCotas()) {
			gerarListaDeChamadaRecursivamente(chamadaId, listaDeChamada, candidatos, curso, cota, cota.getCodigo());
		}
		for (Candidato candidato : listaDeChamada) {
			repo.save(candidato);
		}
		System.out.println(listaDeChamada);
		System.out.println("---------------------------------------------");
		System.out.println(candidatos);
		System.out.println("---------------------------------------------");
		candidatos = candidatos.stream().filter(c -> c.getChamadasConcorridas().get(chamadaId) != null)
				.collect(Collectors.toList());
		System.out.println(candidatos);
		return candidatos;
	}

	public List<Candidato> retrieveList(Integer cursoId, Integer chamadaId) {
		cursoService.findById(cursoId);
		List<Candidato> candidatos = repo.findByCursoId(cursoId);
		return candidatos.stream().filter(c -> c.getChamadasConcorridas().get(chamadaId) != null)
				.collect(Collectors.toList());
	}
	
	public List<Candidato> enrolledList(Integer cursoId) {
		cursoService.findById(cursoId);
		List<Candidato> candidatos = repo.findByCursoId(cursoId);
		return candidatos.stream().filter(c -> c.getMatriculado() == true)
				.collect(Collectors.toList());
	}
	
	public void insertResultFile(List<Candidato> candidatos) {
		for (Candidato candidato : candidatos) {
			repo.save(candidato);
		}
	}
	
	public void delete(Integer cursoId) {
		cursoService.findById(cursoId);
		List<Candidato> candidatos = repo.findByCursoId(cursoId);
		for (Candidato candidato : candidatos) {
			repo.delete(candidato);
		}
	}

	private void gerarListaDeChamadaRecursivamente(Integer chamadaId, List<Candidato> listaDeChamada,
			List<Candidato> candidatos, Curso curso, Cota cota, String tipoDaCota) {
		int posicao = 0;
		List<Candidato> candidatosDaCota = candidatos.stream()
				.filter(c -> c.getCotasAConcorrer().contains(cota.getCodigo())).collect(Collectors.toList());
		candidatosDaCota.sort((a, b) -> a.getPosicao() - b.getPosicao());

		while (cota.getVagas() > 0 && posicao < candidatosDaCota.size()) {
			if (!listaDeChamada.contains(candidatosDaCota.get(posicao))
					&& candidatosDaCota.get(posicao).getConcorrenteAtivo()) {
				candidatosDaCota.get(posicao).getChamadasConcorridas().put(chamadaId, tipoDaCota);
				listaDeChamada.add(candidatosDaCota.get(posicao));
				posicao++;
				cota.setVagas(cota.getVagas() - 1);
			} else {
				posicao++;
			}
		}
		if (cota.getVagas() > 0) {
			Optional<Cota> novaCota = curso.getCotas().stream().filter(c -> c.getId() == cota.getId() - 1).findFirst();
			if (!novaCota.isEmpty()) {
				novaCota.get().setVagas(cota.getVagas());
				gerarListaDeChamadaRecursivamente(chamadaId, listaDeChamada, candidatos, curso, novaCota.get(),
						tipoDaCota);
			}
		}
	}

	private Candidato createCandidato(CandidatoDTO obj, Curso curso) {
		Candidato candidato = new Candidato(obj.getId(), obj.getNome(), obj.getCampus(), obj.getNivelDoCurso(),
				obj.getCotaDeInscricao(), obj.getPontuacao(), obj.getPosicao(), curso);
		candidato = estabelecerQuaisCotasDeveConcorrer(candidato);
		return candidato;
	}

	private Integer[] obterQuantidadeDeVagasParaCadaCotaDoCurso(Integer vagasTotais) {
		Integer[] vagasDoCurso = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		if (vagasTotais < 1) {
			throw new InvalidNumberOfCourseVacancyException("Número de vagas inválido");
		} else if (vagasTotais == 1) {
			vagasDoCurso[0] = 1;
		} else {
			int vagasPublicas = (int) Math.ceil(vagasTotais / 2);
			int vagasUniversais = (int) Math.floor(vagasTotais / 2);

			if (vagasUniversais <= 3) {
				vagasDoCurso[0]++;
				vagasUniversais--;
				for (int i = 2; i >= 0; i--) {
					if (vagasUniversais > 0) {
						vagasDoCurso[i]++;
						vagasUniversais--;
					}
				}
			} else {
				for (int i = 2; i >= 0; i--) {
					vagasDoCurso[i] += (int) Math
							.floor(vagasUniversais * percentuaisParaObtencaoDaQuantidadeDeVagasDeCadaCota[i] * 2) > 0
									? (int) Math.floor(vagasUniversais
											* percentuaisParaObtencaoDaQuantidadeDeVagasDeCadaCota[i] * 2)
									: 1;
					vagasUniversais -= vagasDoCurso[i];
				}
				if (vagasUniversais > 0) {
					for (int i = 2; i >= 0; i--) {
						if (vagasUniversais > 0) {
							vagasDoCurso[i]++;
							vagasUniversais--;
						}
					}
				} else if (vagasUniversais < 0) {
					for (int i = 0; i <= 2; i++) {
						vagasDoCurso[i]--;
						vagasUniversais++;
					}
				}
			}

			if (vagasPublicas <= 8) {
				vagasDoCurso[3]++;
				vagasPublicas--;
				for (int i = 6; i >= 4; i--) {
					if (vagasPublicas > 0) {
						vagasDoCurso[i]++;
						vagasPublicas--;
					}
				}
				for (int i = 9; i >= 7; i--) {
					if (vagasPublicas > 0) {
						vagasDoCurso[i]++;
						vagasPublicas--;
					}
				}
				if (vagasPublicas > 0) {
					vagasDoCurso[10]++;
				}
			} else {
				for (int i = 10; i >= 3; i--) {
					vagasDoCurso[i] += (int) Math
							.floor(vagasPublicas * percentuaisParaObtencaoDaQuantidadeDeVagasDeCadaCota[i] * 2) > 0
									? (int) Math.floor(
											vagasPublicas * percentuaisParaObtencaoDaQuantidadeDeVagasDeCadaCota[i] * 2)
									: 1;
					vagasPublicas -= vagasDoCurso[i];
				}
				if (vagasPublicas > 0) {
					for (int i = 10; i >= 3; i--) {
						if (vagasPublicas > 0) {
							vagasDoCurso[i]++;
							vagasPublicas--;
						}
					}
				} else if (vagasPublicas < 0) {
					for (int i = 3; i <= 10; i++) {
						vagasDoCurso[i]--;
						vagasPublicas++;
					}
				}
			}
		}

		return vagasDoCurso;
	}

	private Candidato estabelecerQuaisCotasDeveConcorrer(Candidato candidato) {
		switch (candidato.getCotaDeInscricao()) {
		case 1:
			candidato.getCotasAConcorrer().add("C1");
			break;
		case 2:
			candidato.getCotasAConcorrer()
					.addAll(Arrays.asList("C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11"));
			candidato.setCotaRendaInferior(true);
			candidato.setCotaPretoPardo(true);
			candidato.setCotaPCD(true);
			candidato.setCotaEscolaPublica(true);
			break;
		case 3:
			candidato.getCotasAConcorrer().addAll(Arrays.asList("C1", "C3", "C5", "C7", "C9", "C11"));
			candidato.setCotaRendaInferior(true);
			candidato.setCotaPretoPardo(true);
			candidato.setCotaEscolaPublica(true);
			break;
		case 4:
			candidato.getCotasAConcorrer().addAll(Arrays.asList("C1", "C4", "C5", "C8", "C9", "C10"));
			candidato.setCotaRendaInferior(true);
			candidato.setCotaPCD(true);
			candidato.setCotaEscolaPublica(true);
			break;
		case 5:
			candidato.getCotasAConcorrer().addAll(Arrays.asList("C1", "C5", "C9"));
			candidato.setCotaRendaInferior(true);
			candidato.setCotaEscolaPublica(true);
			break;
		case 6:
			candidato.getCotasAConcorrer().addAll(Arrays.asList("C1", "C6", "C7", "C8", "C9", "C10", "C11"));
			candidato.setCotaPretoPardo(true);
			candidato.setCotaPCD(true);
			candidato.setCotaEscolaPublica(true);
			break;
		case 7:
			candidato.getCotasAConcorrer().addAll(Arrays.asList("C1", "C7", "C9", "C11"));
			candidato.setCotaPretoPardo(true);
			candidato.setCotaEscolaPublica(true);
			break;
		case 8:
			candidato.getCotasAConcorrer().addAll(Arrays.asList("C1", "C8", "C9", "C10"));
			candidato.setCotaPCD(true);
			candidato.setCotaEscolaPublica(true);
			break;
		case 9:
			candidato.getCotasAConcorrer().addAll(Arrays.asList("C1", "C9"));
			candidato.setCotaEscolaPublica(true);
			break;
		case 10:
			candidato.getCotasAConcorrer().addAll(Arrays.asList("C1", "C10"));
			candidato.setCotaPCD(true);
			break;
		case 11:
			candidato.getCotasAConcorrer().addAll(Arrays.asList("C1", "C11"));
			candidato.setCotaPretoPardo(true);
			break;
		case 12:
			candidato.getCotasAConcorrer()
					.addAll(Arrays.asList("C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10"));
			candidato.setCotaRendaInferior(true);
			candidato.setCotaIndigena(true);
			candidato.setCotaPCD(true);
			candidato.setCotaEscolaPublica(true);
			break;
		case 13:
			candidato.getCotasAConcorrer().addAll(Arrays.asList("C1", "C3", "C5", "C7", "C9"));
			candidato.setCotaRendaInferior(true);
			candidato.setCotaIndigena(true);
			candidato.setCotaEscolaPublica(true);
			break;
		case 16:
			candidato.getCotasAConcorrer().addAll(Arrays.asList("C1", "C6", "C7", "C8", "C9", "C10"));
			candidato.setCotaIndigena(true);
			candidato.setCotaPCD(true);
			candidato.setCotaEscolaPublica(true);
			break;
		case 17:
			candidato.getCotasAConcorrer().addAll(Arrays.asList("C1", "C7", "C9"));
			candidato.setCotaIndigena(true);
			candidato.setCotaEscolaPublica(true);
			break;
		}
		return candidato;
	}

}
