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
import br.com.academia.poo.model.Cliente;
import br.com.academia.poo.model.Funcionario;
import br.com.academia.poo.repository.FuncionarioRepository;
import io.swagger.annotations.ApiOperation;


	@RestController
	@RequestMapping("/funcionarios")
	public class FuncionarioEndpoint {

		private static FuncionarioRepository funcionarios;
		
		@Autowired
		public FuncionarioEndpoint(FuncionarioRepository funcionarios) {
			super();
			this.funcionarios = funcionarios;
		}
		
		@GetMapping(value = "/listarFuncionarios")
		@ApiOperation(value = "Mostra uma lista de funcionários já cadastrados", response = Funcionario.class)
		public ResponseEntity<?> listarAllFuncionarios() {
			return new ResponseEntity<>(funcionarios.findAll(), HttpStatus.OK);
		}
		
		@GetMapping(value = "/{id}")
		@ApiOperation(value = "Mostra um funcionário específico já cadastrado", response = Funcionario.class)
		public ResponseEntity<?> listarFuncionario(@PathVariable("id") Long id){
			verificarFuncionarioExiste(id);
			Funcionario funcionario = funcionarios.findById(id).get();
			return new ResponseEntity<>(funcionario,HttpStatus.OK);
		}
		
		@PostMapping(value = "/cadastraFuncionario")
		@ApiOperation(value = "Cadastra um funcionário na lista", response = Funcionario.class)
		public ResponseEntity<?> cadastrarFuncionario(@RequestBody Funcionario funcionario) {
			Funcionario funcionarioSalvo = funcionario;
			
			return new ResponseEntity<>(funcionarios.save(funcionarioSalvo), HttpStatus.CREATED);
		}
		
		@PutMapping(value = "/alteraFuncionario")
		@ApiOperation(value = "Altera os dados  cadastrados de um funcionário", response = Funcionario.class)
		public ResponseEntity<?> atualizarFuncionario(@RequestBody Funcionario funcionario){
			verificarFuncionarioExiste(funcionario.getId());
			Funcionario f = funcionario;
			funcionarios.save(funcionario);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		@DeleteMapping(value = "/{id}")
		@ApiOperation(value = "Deleta da lista um funcionário cadastrado", response = Funcionario.class)
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

