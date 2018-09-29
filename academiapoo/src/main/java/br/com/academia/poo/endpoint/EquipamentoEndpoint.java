package br.com.academia.poo.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.academia.poo.error.ResourceNotFoundException;
import br.com.academia.poo.model.Equipamento;
import br.com.academia.poo.repository.EquipamentoRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoEndpoint {
	
	private static EquipamentoRepository equipamentos;
	
	@Autowired
	public EquipamentoEndpoint(EquipamentoRepository equipamentos) {
		this.equipamentos = equipamentos;
	}

	// metodo get
	@GetMapping
	@ApiOperation(value = "Mostra uma lista de equipamentos já cadastrados", response = Equipamento.class)
	public ResponseEntity<?> listAllEquipamentos() {
		return new ResponseEntity<>(equipamentos.findAll(), HttpStatus.OK);
	}

	// metodo get
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Mostra em equipamento específico", response = Equipamento.class)
	public ResponseEntity<?> getEquipamentoById(@PathVariable("id") Long id) {
		verificarEquipamentosExiste(id);
		Equipamento equipamento = equipamentos.findById(id).get();
		return new ResponseEntity<>(equipamento, HttpStatus.OK);
	}

	// metodo post
	@PostMapping(path = "/cadastrarEquipamento")
	@ApiOperation(value = "Cadastra um novo equipamento na lista", response = Equipamento.class)
	public ResponseEntity<?> saveEquipamento(@RequestBody Equipamento equipamento) {
		return new ResponseEntity<>(equipamentos.save(equipamento), HttpStatus.OK);
	}

	// metodo delete
	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Deleta da lista um equipamento cadastrado", response = Equipamento.class)
	public ResponseEntity<?> deleteEquipamento(@PathVariable Long id) {
		verificarEquipamentosExiste(id);
		equipamentos.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// metodo put
	@PutMapping(path = "/alteraEquipamento")
	@ApiOperation(value = "Altera os dados cadastrados de um equipamento", response = Equipamento.class)
	public ResponseEntity<?> updateEquipamento(@RequestBody Equipamento equipamento) {
		verificarEquipamentosExiste(equipamento.getId());
		Equipamento e = equipamento;
		equipamentos.save(e);
		return new ResponseEntity<>(e, HttpStatus.OK);
	}

	private void verificarEquipamentosExiste(Long id) {
		if (!equipamentos.findById(id).isPresent())
			throw new ResourceNotFoundException("Equipamento não encontrado pelo ID: " + id);
	}
}
