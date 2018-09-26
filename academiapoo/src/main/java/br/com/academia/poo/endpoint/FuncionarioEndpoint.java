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
import br.com.academia.poo.model.Funcionario;
import br.com.academia.poo.repository.FuncionarioRepository;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioEndpoint {

	private static FuncionarioRepository funcionarios;
	
	@Autowired
	public FuncionarioEndpoint(FuncionarioRepository funcionarios) {
		super();
		this.funcionarios = funcionarios;
	}
	
	@GetMapping
	public ResponseEntity<?> listarAllFuncionarios() {
		return new ResponseEntity<>(funcionarios.findAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> listarFuncionario(@PathVariable("id") Long id){
		verificarFuncionarioExiste(id);
		Funcionario funcionario = funcionarios.findById(id).get();
		return new ResponseEntity<>(funcionario,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> cadastrarFuncionario(@RequestBody Funcionario funcionario) {
		Funcionario funcionarioSalvo = funcionario;
		
		return new ResponseEntity<>(funcionarios.save(funcionarioSalvo), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<?> atualizarFuncionario(@RequestBody Funcionario funcionario){
		verificarFuncionarioExiste(funcionario.getId());
		funcionarios.deleteById(funcionario.getId());
		Funcionario funcionarioAlterado = funcionario;
		funcionarios.save(funcionarioAlterado);
		return new ResponseEntity<>(funcionarioAlterado,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deletarFuncionario(@PathVariable Long id){
		verificarFuncionarioExiste(id);
		funcionarios.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void verificarFuncionarioExiste(Long id) {
		if (!funcionarios.findById(id).isPresent())
			throw new ResourceNotFoundException("funcionario não encontrado pelo ID: " + id);
	}
	
}
