package br.com.academia.poo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.academia.poo.model.Funcionario;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> {
	List<Funcionario> findByName(String name); 
	

}
