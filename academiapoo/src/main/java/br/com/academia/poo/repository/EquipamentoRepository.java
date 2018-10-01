package br.com.academia.poo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.academia.poo.model.Equipamento;


public interface EquipamentoRepository extends CrudRepository<Equipamento, Integer> {
	List<Equipamento> findByName(String name);
}