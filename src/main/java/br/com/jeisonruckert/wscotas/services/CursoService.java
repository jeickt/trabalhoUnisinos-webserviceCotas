package br.com.jeisonruckert.wscotas.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.jeisonruckert.wscotas.entities.Curso;
import br.com.jeisonruckert.wscotas.repositories.CursoRepository;
import br.com.jeisonruckert.wscotas.services.exceptions.CourseWithoutCandidatesException;
import br.com.jeisonruckert.wscotas.services.exceptions.ResourceNotFoundException;

@Service
public class CursoService {

	private final CursoRepository repo;

	public CursoService(CursoRepository repository) {
		this.repo = repository;
	}
	
	public List<Curso> findAll() {
		List<Curso> cursos = repo.findAll();
		cursos = cursos.stream().filter(c -> !c.getCandidatos().isEmpty()).collect(Collectors.toList());
		return cursos;
	}
	
	public Curso findById(Integer id) {
		Optional<Curso> curso = repo.findById(id);
		if (curso.isEmpty()) {
			throw new ResourceNotFoundException("Curso não encontrado.");
		}
		if (curso.get().getCandidatos().isEmpty()) {
			throw new CourseWithoutCandidatesException("Curso sem candidatos.");
		}
		return curso.get();
	}

	public Curso save(Curso curso) {
		return repo.save(curso);
	}

}
