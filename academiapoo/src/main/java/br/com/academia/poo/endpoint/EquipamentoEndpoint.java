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



@RestController
@RequestMapping("/equipamentos")
public class EquipamentoEndpoint {

	private final EquipamentoRepository equipamentos;

	@Autowired
	public EquipamentoEndpoint(EquipamentoRepository equipamentos) {
		super();
		this.equipamentos = equipamentos;
	}

	// metodo get
	@GetMapping
	public ResponseEntity<?> listAllEquipamentos() {
		return new ResponseEntity<>(equipamentos.findAll(), HttpStatus.OK);
	}

	// metodo get
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getEquipamentoById(@PathVariable("id") Long id) {
		verificarEquipamentoExiste(id);
		Equipamento equipamento = equipamentos.findById(id).get();
		return new ResponseEntity<>(equipamento, HttpStatus.OK);
	}

	// metodo post
	@PostMapping
	public ResponseEntity<?> saveEquipamento(@RequestBody Equipamento equipamento) {
		return new ResponseEntity<>(equipamentos.save(equipamento), HttpStatus.OK);
	}

	// metodo delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEquipamento(@PathVariable Long id) {
		verificarEquipamentoExiste(id);
		equipamentos.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// metodo put
	@PutMapping
	public ResponseEntity<?> updateEquipamento(@RequestBody Equipamento equipamento) {
		verificarEquipamentoExiste(equipamento.getId());
		equipamentos.save(equipamento);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void verificarEquipamentoExiste(Long id){ 
		if(!equipamentos.findById(id).isPresent()) 
			throw new ResourceNotFoundException("equipamento não encontrado pelo ID: " + id); 
	}
}