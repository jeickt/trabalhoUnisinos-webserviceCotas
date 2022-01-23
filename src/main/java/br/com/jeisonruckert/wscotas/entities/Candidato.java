package br.com.jeisonruckert.wscotas.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_candidato")
public class Candidato implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String nome;
	private String campus;
	private String nivelDoCurso;
	private Integer cotaDeInscricao;
	private Double pontuacao;
	private Integer posicao;
	private Boolean concorrenteAtivo;
	private Boolean matriculado;
	private Boolean cotaEscolaPublica;
	private Boolean cotaRendaInferior;
	private Boolean cotaPretoPardo;
	private Boolean cotaIndigena;
	private Boolean cotaPCD;
	
	@ManyToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;
	
	@ElementCollection(targetClass=String.class)
	private Set<String> cotasAConcorrer = new HashSet<>();
	
	@ElementCollection
	private Map<Integer, String> chamadasConcorridas = new HashMap<Integer, String>() 
	{{put(1, null); put(2, null); put(3, null); put(4, null);}};
	
	public Candidato() {
	}

	public Candidato(String id, String nome, String campus, String nivelDoCurso, Integer cotaDeInscricao,
			Double pontuacao, Integer posicao, Curso curso) {
		this.id = id;
		this.nome = nome;
		this.campus = campus;
		this.nivelDoCurso = nivelDoCurso;
		this.cotaDeInscricao = cotaDeInscricao;
		this.pontuacao = nivelDoCurso.equals("SUPERIOR") ? pontuacao : pontuacao * 2.5;
		this.posicao = posicao;
		this.concorrenteAtivo = true;
		this.matriculado = false;
		this.curso = curso;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public String getNivelDoCurso() {
		return nivelDoCurso;
	}

	public void setNivelDoCurso(String nivelDoCurso) {
		this.nivelDoCurso = nivelDoCurso;
	}

	public Integer getCotaDeInscricao() {
		return cotaDeInscricao;
	}

	public void setCotaDeInscricao(Integer cotaDeInscricao) {
		this.cotaDeInscricao = cotaDeInscricao;
	}

	public Double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Double pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public Boolean getConcorrenteAtivo() {
		return concorrenteAtivo;
	}

	public void setConcorrenteAtivo(Boolean concorrenteAtivo) {
		this.concorrenteAtivo = concorrenteAtivo;
	}

	public Boolean getMatriculado() {
		return matriculado;
	}

	public void setMatriculado(Boolean matriculado) {
		this.matriculado = matriculado;
	}

	public Boolean getCotaEscolaPublica() {
		return cotaEscolaPublica;
	}

	public void setCotaEscolaPublica(Boolean cotaEscolaPublica) {
		this.cotaEscolaPublica = cotaEscolaPublica;
	}

	public Boolean getCotaRendaInferior() {
		return cotaRendaInferior;
	}

	public void setCotaRendaInferior(Boolean cotaRendaInferior) {
		this.cotaRendaInferior = cotaRendaInferior;
	}

	public Boolean getCotaPretoPardo() {
		return cotaPretoPardo;
	}

	public void setCotaPretoPardo(Boolean cotaPretoPardo) {
		this.cotaPretoPardo = cotaPretoPardo;
	}

	public Boolean getCotaIndigena() {
		return cotaIndigena;
	}

	public void setCotaIndigena(Boolean cotaIndigena) {
		this.cotaIndigena = cotaIndigena;
	}

	public Boolean getCotaPCD() {
		return cotaPCD;
	}

	public void setCotaPCD(Boolean cotaPCD) {
		this.cotaPCD = cotaPCD;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Set<String> getCotasAConcorrer() {
		return cotasAConcorrer;
	}

	public Map<Integer, String> getChamadasConcorridas() {
		return chamadasConcorridas;
	}

	@Override
	public String toString() {
		return "Candidato [id=" + id + ", nome=" + nome + ", campus=" + campus + ", curso=" + curso +
				", cotaDeInscricao=" + cotaDeInscricao	+  "]";
	}
	
	

}
