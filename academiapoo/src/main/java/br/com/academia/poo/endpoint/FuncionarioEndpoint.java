package br.com.academia.poo.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import br.com.academia.poo.repository.ClienteRepository;
import br.com.academia.poo.repository.FuncionarioRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioEndpoint {
	
	private static FuncionarioRepository funcionarios;
	
	
	@Autowired
	public FuncionarioEndpoint(FuncionarioRepository funcionarios) {
		super();
		this.funcionarios = funcionarios;
	}
/////Metodo Get
	
	@GetMapping
	@ApiOperation(value = "Mostra uma lista de funcionários já cadastrados", response = Funcionario.class, notes = "Essa operação mostra um registro dos funcionários cadastrados.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna uma lista de funcionarios com uma mensagem de sucesso", response = Funcionario.class),
			@ApiResponse(code = 500, message = "Caso tenhamos algum erro, não retornamos nada", response = Funcionario.class)

	})
	public ResponseEntity<?> listarAllFuncionarios() {
		return new ResponseEntity<>(funcionarios.findAll(), HttpStatus.OK);
	}

//////GetById	
	@GetMapping(value = "/{id}")
	@ApiOperation(value = "Mostra um funcionário específico já cadastrado", response = Funcionario.class, notes = "Essa operação mostra um registro de um funcionário específico.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna uma funcionario específico com uma mensagem de sucesso", response = Funcionario.class),
			@ApiResponse(code = 500, message = "Caso tenhamos algum não retornamos nada", response = Funcionario.class)

	})
	public ResponseEntity<?> listarFuncionario(@PathVariable("id") int id) {
		verificarFuncionarioExiste(id);
		Funcionario funcionario = funcionarios.findById(id).get();
		return new ResponseEntity<>(funcionario, HttpStatus.OK);
	}

	////Metodo Post
	
	@PostMapping( consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "Cadastra um funcionário na lista", response = Funcionario.class, notes = "Essa operação salva um novo registro com as informações de funcionário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna um funcionário com uma mensagem de sucesso", response = Funcionario.class),
			@ApiResponse(code = 500, message = "Caso tenhamos algum erro, não retornamos nada", response = Funcionario.class)

	})
	public ResponseEntity<?> cadastrarFuncionario(@RequestBody Funcionario funcionario) {
		Funcionario funcionarioSalvo = funcionario;
		return new ResponseEntity<>(funcionarios.save(funcionario), HttpStatus.OK);
	}

	////Metodo Put
	
	@PutMapping(path = "/alteraFuncionario")
	@ApiOperation(value = "Altera os dados  cadastrados de um funcionário", response = Funcionario.class, notes = "Esta operação altera os dados de um funcionário")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna um funcionário com uma mensagem de sucesso", response = Funcionario.class),
			@ApiResponse(code = 500, message = "Caso tenhamos algum erro, retornaremos o mesmo", response = Funcionario.class)
			
	})
	public ResponseEntity<?> atualizarFuncionario(@RequestBody Funcionario funcionario) {
		verificarFuncionarioExiste(funcionario.getId());
		Funcionario f = funcionario;
		funcionarios.save(f);
		return new ResponseEntity<>(f, HttpStatus.OK);
	}

	///Metodo Delete
	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Deleta da lista um funcionário cadastrado", response = Funcionario.class, notes = "Esta operação deleta um funcionário da lista")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Retorna um funcionário com uma mensagem de sucesso", response = Funcionario.class),
			@ApiResponse(code = 500, message = "Caso tenhamos algum erro, retornaremos o mesmo", response = Funcionario.class)
			
	})
	public ResponseEntity<?> deletarFuncionario(@PathVariable int id) {
		verificarFuncionarioExiste(id);
		funcionarios.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void verificarFuncionarioExiste(int id) {
		if (!funcionarios.findById(id).isPresent())
			throw new ResourceNotFoundException("Funcionario não encontrado pelo ID: " + id);
	}

}
