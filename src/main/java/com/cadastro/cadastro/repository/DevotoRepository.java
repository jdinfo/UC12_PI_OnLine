package com.cadastro.cadastro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query.*;
import org.springframework.data.repository.CrudRepository;
import com.cadastro.cadastro.models.Devoto;

public interface DevotoRepository extends CrudRepository<Devoto, String> {
	Devoto findByCodigo(long codigo);
	List<Devoto> findByNome(String nome);
	
}
