package br.com.jeisonruckert.wscotas.services;

import org.springframework.stereotype.Service;

import br.com.jeisonruckert.wscotas.entities.Cota;
import br.com.jeisonruckert.wscotas.repositories.CotaRepository;

@Service
public class CotaService {

	private final CotaRepository repo;

	public CotaService(CotaRepository repository) {
		this.repo = repository;
	}

	public Cota salvar(Cota cota) {
		return repo.save(cota);
	}

}
