package br.com.jeisonruckert.wscotas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jeisonruckert.wscotas.entities.Candidato;

public interface CandidatoRepository extends JpaRepository<Candidato, String> {

}
