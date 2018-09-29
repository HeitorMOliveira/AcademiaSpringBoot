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
import br.com.academia.poo.model.Academia;
import br.com.academia.poo.model.Equipamento;
import br.com.academia.poo.model.Funcionario;
import br.com.academia.poo.repository.AcademiaRepository;
import br.com.academia.poo.repository.EquipamentoRepository;
import br.com.academia.poo.repository.FuncionarioRepository;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/academia")
public class AcademiaEndpoint {

	private static AcademiaRepository academias;

	@Autowired
	public AcademiaEndpoint(AcademiaRepository academias) {

		this.academias = academias;
	}

	// metodo get
	@GetMapping
	@ApiOperation(value = "Mostra uma lista de academiass já cadastradas", response = Equipamento.class)
	public ResponseEntity<?> listAllAcademias() {
		return new ResponseEntity<>(academias.findAll(), HttpStatus.OK);
	}

	// metodo get
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Mostra uma academia específica", response = Academia.class)
	public ResponseEntity<?> getAcademiaById(@PathVariable("id") Long id) {
		verificarAcademiaExiste(id);
		Academia academia = academias.findById(id).get();
		return new ResponseEntity<>(academia, HttpStatus.OK);
	}

	// metodo post
	@PostMapping
	@ApiOperation(value = "Cadastra uma nova academia na lista", response = Equipamento.class)
	public ResponseEntity<?> saveAcademia(@RequestBody Academia academia) {
		return new ResponseEntity<>(academias.save(academia), HttpStatus.OK);
	}

	// metodo delete
	@DeleteMapping(path = "{id}")
	@ApiOperation(value = "Deleta da lista uma academia cadastrada", response = Academia.class)
	public ResponseEntity<?> deleteAcademia(@PathVariable Long id) {
		verificarAcademiaExiste(id);
		academias.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// metodo put
	@PutMapping(path = "/alterarAcademia")
	@ApiOperation(value = "Altera os dados cadastrados de uma academia", response = Academia.class)
	public ResponseEntity<?> updateEquipamento(@RequestBody Academia academia) {
		verificarAcademiaExiste(academia.getId());
		Academia e = academia;
		academias.save(e);
		return new ResponseEntity<>(e, HttpStatus.OK);
	}

	private void verificarAcademiaExiste(Long id) {
		if (!academias.findById(id).isPresent())
			throw new ResourceNotFoundException("Academia não encontrado pelo ID: " + id);
	}
}
