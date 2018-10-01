package br.com.academia.poo;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jayway.restassured.RestAssured;


@FixMethodOrder(MethodSorters.NAME_ASCENDING) // DEFINIR ORDEM DE TESTE POR NOME
public class TesteEndpointCliente {

	private static final String SERVIDOR = "http://localhost:8080";

	public TesteEndpointCliente() {
		RestAssured.baseURI = SERVIDOR;
	}

	@Test
	public void test1CriarCliente() {

		String vanessa = "{\"id\" : \"1\", \"name\":\"vanessa\", \"telefone\" : \"40028922\","
				+ " \"email\" : \"vanessa@email.com\", \"endereco\": \"80\", \"dataNascimento\" : \"20 de Maio de 1998\","
				+ " \"cpf\" : \"1234-5678\", \"plano\" : \"academia\"}";

		String hugo = "{\"id\" : \"2\", \"name\":\"hugo\","
				+ "\"telefone\" : \"40028922\", \"email\" : \"hugo@email.com\","
				+ "\"endereco\" : \"80\", \"dataNascimento\" : \"20 de Maio de 1998\", \"cpf\" : \"1234-5678\", \"plano\" : \"academia\"}";

		
		
		
		given().contentType("application/json").body(vanessa)
		.when().post("/clientes/")
		.then().statusCode(200)
		.body("id", greaterThanOrEqualTo(1))
		.body("name", containsString("vanessa"))
		.body("telefone", greaterThanOrEqualTo(40028922))
		.body("email", containsString("vanessa@email.com"))
		.body("endereco", containsString("80"))
		.body("dataNascimento", containsString("20 de Maio de 1998"))
		.body("cpf", containsString("1234-5678"))
		.body("plano", containsString("academia"));
		
		given().contentType("application/json").body(hugo)
		.when().post("/clientes")
		.then().statusCode(200)
		.body("id", greaterThanOrEqualTo(2))
		.body("name", containsString("hugo"))
		.body("telefone", greaterThanOrEqualTo(40028922))
		.body("email", containsString("hugo@email.com"))
		.body("endereco", containsString("80"))
		.body("dataNascimento", containsString("20 de Maio de 1998"))
		.body("cpf", containsString("1234-5678"))
		.body("plano", containsString("academia"));
		
	}

	
	@Test 
	public void test2PegarCliente() {
		given().contentType("application/json")
		.when().get("/clientes/1")
		.then().statusCode(200)
		.body("id", greaterThanOrEqualTo(1));
	}
	
	@Test
	public void test3ListarClientes() {
		given().contentType("application/json")
		.when().get("/clientes/")
		.then().statusCode(200);
	}
	
	@Test
	public void test4DeletarCliente() {
		given().contentType("application/json")
		.when().delete("/clientes/{id}", 2)
		.then().statusCode(200);
		
	}
	
	@Test
	public void test5MudarCliente() {
		
		String alisson = "{\"id\" : \"1\", \"name\":\"alisson\","
				+ "\"telefone\" : \"40028922\", \"email\" : \"alisson@email.com\","
				+ "\"endereco\" : \"80\", \"dataNascimento\" : \"20 de Maio de 1998\", \"cpf\" : \"1234-5678\", \"plano\" : \"academia\"}";

		given().contentType("application/json")
		.when().delete("/clientes/{id}", 1)
		.then().statusCode(200);
		
		given().contentType("application/json").body(alisson)
		.when().post("/clientes/")
		.then().statusCode(200)
		.body("id", greaterThanOrEqualTo(1))
		.body("name", containsString("alisson"))
		.body("telefone", greaterThanOrEqualTo(40028922))
		.body("email", containsString("alisson@email.com"))
		.body("endereco", containsString("80"))
		.body("dataNascimento", containsString("20 de Maio de 1998"))
		.body("cpf", containsString("1234-5678"))
		.body("plano", containsString("academia"));
		
		
		
	}
}
