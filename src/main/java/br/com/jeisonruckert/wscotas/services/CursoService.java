package br.com.jeisonruckert.wscotas.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.jeisonruckert.wscotas.entities.Curso;
import br.com.jeisonruckert.wscotas.repositories.CursoRepository;
import br.com.jeisonruckert.wscotas.services.exceptions.ResourceNotFoundException;

@Service
public class CursoService {

	private final CursoRepository repo;

	public CursoService(CursoRepository repository) {
		this.repo = repository;
	}
	
	public Curso findById(Integer id) {
		Optional<Curso> curso = repo.findById(id);
		if (curso.isEmpty()) {
			throw new ResourceNotFoundException(id);
		}
		return curso.get();
	}

	public Curso save(Curso curso) {
		return repo.save(curso);
	}

}
