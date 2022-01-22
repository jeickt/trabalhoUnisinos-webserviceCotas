package br.com.jeisonruckert.wscotas.entities;

import java.io.Serializable;

public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	
	private Cota[] cotas = new Cota[11];

	public Curso() {
	}

	public Curso(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cota[] getCotas() {
		return cotas;
	}

}
