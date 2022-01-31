package br.com.jeisonruckert.wscotas.entities.DTO;

public class CandidatoDTO {

	private String id;
	private String nome;
	private String campus;
	private String nivelDoCurso;
	private String curso;
	private Integer cotaDeInscricao;
	private Double pontuacao;
	private Integer posicao;
	
	public CandidatoDTO() {
	}

	public CandidatoDTO(String id, String nome, String campus, String nivelDoCurso, String curso,
			Integer cotaDeInscricao, Double pontuacao, Integer posicao) {
		super();
		this.id = id;
		this.nome = nome;
		this.campus = campus;
		this.nivelDoCurso = nivelDoCurso;
		this.curso = curso;
		this.cotaDeInscricao = cotaDeInscricao;
		this.pontuacao = pontuacao;
		this.posicao = posicao;
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

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
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
	
}
