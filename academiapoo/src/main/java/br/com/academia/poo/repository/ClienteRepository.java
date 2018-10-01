package br.com.academia.poo.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.academia.poo.model.Cliente;


public interface ClienteRepository extends CrudRepository<Cliente, Integer> {
	List<Cliente> findByName(String name);
}