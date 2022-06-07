package br.com.jeisonruckert.wscotas.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_curso")
public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String nome;
	
	@JsonIgnore
	@OneToMany(mappedBy="curso")
	private List<Cota> cotas = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="curso")
	private List<Candidato> candidatos = new ArrayList<>();

	public Curso() {
	}

	public Curso(String nome) {
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

	public List<Cota> getCotas() {
		return cotas;
	}
	
	public List<Candidato> getCandidatos() {
		return candidatos;
	}

	@Override
	public String toString() {
		return "Curso [nome=" + nome + ", cotas=" + cotas + "]";
	}

}
