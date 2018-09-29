package br.com.academia.poo.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.academia.poo.model.Academia;

public interface AcademiaRepository extends CrudRepository<Academia, Long>{
	
}
