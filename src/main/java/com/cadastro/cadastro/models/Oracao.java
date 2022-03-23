package com.cadastro.cadastro.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Oracao {
	
	
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(unique = true)
	private String versao;
	
	@NotEmpty
	private String tituloOracao;
	
	@ManyToOne
	private Devoto devoto;

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public String getTituloOracao() {
		return tituloOracao;
	}

	public void setTituloOracao(String tituloOracao) {
		this.tituloOracao = tituloOracao;
	}

	public Devoto getDevoto() {
		return devoto;
	}

	public void setDevoto(Devoto devoto) {
		this.devoto = devoto;
	}

	
	
}
