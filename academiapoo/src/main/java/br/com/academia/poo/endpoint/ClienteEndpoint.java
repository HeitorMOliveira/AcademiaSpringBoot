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
import br.com.academia.poo.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteEndpoint {

	private final ClienteRepository clientes;

	@Autowired
	public ClienteEndpoint(ClienteRepository clientes) {
		super();
		this.clientes = clientes;
	}

	// metodo get
	@GetMapping
	public ResponseEntity<?> listAllClientes() {
		return new ResponseEntity<>(clientes.findAll(), HttpStatus.OK);
	}

	// metodo get
	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getClienteById(@PathVariable("id") Long id) {
		verificarClienteExiste(id);
		Cliente cliente = clientes.findById(id).get();
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	// metodo post
	@PostMapping
	public ResponseEntity<?> saveCliente(@RequestBody Cliente cliente) {
		return new ResponseEntity<>(clientes.save(cliente), HttpStatus.OK);
	}

	// metodo delete
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCliente(@PathVariable Long id) {
		verificarClienteExiste(id);
		clientes.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// metodo put
	@PutMapping
	public ResponseEntity<?> updateCliente(@RequestBody Cliente cliente) {
		verificarClienteExiste(cliente.getId());
		clientes.save(cliente);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void verificarClienteExiste(Long id) {
		if (!clientes.findById(id).isPresent())
			throw new ResourceNotFoundException("cliente não encontrado pelo ID: " + id);
	}
}