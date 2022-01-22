package br.com.jeisonruckert.wscotas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jeisonruckert.wscotas.entities.Cota;

public interface CotaRepository extends JpaRepository<Cota, Integer> {

}
