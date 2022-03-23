package com.cadastro.cadastro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.cadastro.cadastro.models.Oracao;
import com.cadastro.cadastro.models.Devoto;

public interface OracaoRepositoy extends CrudRepository<Oracao, String> {
	
	Iterable<Oracao>findByDevoto(Devoto devoto);
	
	Oracao findByVersao(String versao);
	
	Oracao findById(long id);
	
	List <Oracao>findByTituloOracao(String tituloOracao);
}
