package br.com.jeisonruckert.wscotas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jeisonruckert.wscotas.entities.Candidato;

public interface CandidatoRepository extends JpaRepository<Candidato, String> {
	
	List<Candidato> findByCursoId(Integer cursoId);

}
