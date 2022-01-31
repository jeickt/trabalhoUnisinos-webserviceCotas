package br.com.jeisonruckert.wscotas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jeisonruckert.wscotas.entities.Curso;

public interface CursoRepository extends JpaRepository<Curso, Integer> {

}
