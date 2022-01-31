package br.com.jeisonruckert.wscotas.entities;

import java.io.Serializable;

public class Cota implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer peso;
	private Integer vagas;
	
	public Cota() {
	}

	public Cota(Integer peso, Integer vagas) {
		this.peso = peso;
		this.vagas = vagas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public Integer getVagas() {
		return vagas;
	}

	public void setVagas(Integer vagas) {
		this.vagas = vagas;
	}

}
