package br.com.jeisonruckert.wscotas.services;

import org.springframework.stereotype.Service;

import br.com.jeisonruckert.wscotas.entities.Curso;
import br.com.jeisonruckert.wscotas.repositories.CursoRepository;

@Service
public class CursoService {

	private final CursoRepository repo;

	public CursoService(CursoRepository repository) {
		this.repo = repository;
	}

	public Curso save(Curso curso) {
		return repo.save(curso);
	}

}
