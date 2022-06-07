package br.com.jeisonruckert.wscotas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.jeisonruckert.wscotas.entities.Candidato;
import br.com.jeisonruckert.wscotas.entities.Curso;
import br.com.jeisonruckert.wscotas.services.CandidatoService;

@RunWith(SpringRunner.class)
@SpringBootTest
class WsCotasApplicationTests {

	@Autowired
	protected CandidatoService service;

	@Test
	void gerarListaDePrimeiraChamadaCasoSemRedistribuicaoDeVaga() {
		String fileDir = "." + File.separator + "curso2.csv";
		Path path = Paths.get(fileDir);
		byte[] csvFile = null;
		try {
			csvFile = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		service.insercaoDeArquivoPrincipal(csvFile);
		
		List<Candidato> candidatos = service.prepararGeracaoDeLista(1, 1);

		Assert.assertEquals(candidatos.get(3).getNome(), "4");
	}
	
	@Test
	void gerarListaDePrimeiraChamadaCasoComRedistribuicaoDeVaga() {
		String fileDir = "." + File.separator + "curso1.csv";
		Path path = Paths.get(fileDir);
		byte[] csvFile = null;
		try {
			csvFile = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		service.insercaoDeArquivoPrincipal(csvFile);
		
		List<Candidato> candidatos = service.prepararGeracaoDeLista(2, 1);

		Assert.assertEquals(candidatos.get(29).getNome(), "134");
	}
	
	@Test
	void gerarQuantidadeDeVagasPorCota() {
		Integer[] vagasDoCurso = service.obterQuantidadeDeVagasParaCadaCotaDoCurso(30);

		Assert.assertEquals(4, (int) vagasDoCurso[3]);

	}

}
