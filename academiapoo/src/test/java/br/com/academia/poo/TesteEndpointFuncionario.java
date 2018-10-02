package br.com.academia.poo;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jayway.restassured.RestAssured;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // DEFINIR ORDEM DE TESTE POR NOME
public class TesteEndpointFuncionario {

	private static final String SERVIDOR = "http://localhost:8080";

	public TesteEndpointFuncionario() {
		RestAssured.baseURI = SERVIDOR;
	}

	@Test
	public void test1CriarFuncionario() {

		String mateus = "{\"id\" : \"1\", \"name\":\"mateus\", \"telefone\" : \"40028922\","
				+ " \"email\" : \"mateus@email.com\", \"endereco\": \"80\", \"dataNascimento\" : \"20 de Maio de 1998\","
				+ " \"cpf\" : \"1234-5678\", \"salario\" : \"500\", \"cargaHoraria\" : \"22H\"}";

		String douglas = "{\"id\" : \"2\", \"name\":\"douglas\","
				+ "\"telefone\" : \"40028922\", \"email\" : \"douglas@email.com\","
				+ "\"endereco\" : \"80\", \"dataNascimento\" : \"20 de Maio de 1998\", \"cpf\" : \"1234-5678\", \"salario\" : \"1000\", \"cargaHoraria\" : \"44H\"}";

		given().contentType("application/json").body(mateus).when().post("/funcionarios").then().statusCode(200)
				.body("id", greaterThanOrEqualTo(1)).body("name", containsString("mateus"))
				.body("telefone", greaterThanOrEqualTo(40028922)).body("email", containsString("mateus@email.com"))
				.body("endereco", containsString("80")).body("dataNascimento", containsString("20 de Maio de 1998"))
				.body("cpf", containsString("1234-5678")).body("salario", greaterThanOrEqualTo(500))
				.body("cargaHoraria",containsString("22H"));

		given().contentType("application/json").body(douglas).when().post("/funcionarios").then().statusCode(200)
				.body("id", greaterThanOrEqualTo(2)).body("name", containsString("douglas"))
				.body("telefone", greaterThanOrEqualTo(40028922)).body("email", containsString("douglas@email.com"))
				.body("endereco", containsString("80")).body("dataNascimento", containsString("20 de Maio de 1998"))
				.body("cpf", containsString("1234-5678")).body("salario", greaterThanOrEqualTo(1000))
				.body("cargaHoraria", containsString("44H"));

	}

	@Test
	public void test2PegarFuncionario() {
		given().contentType("application/json").when().get("/funcionarios/{id}", 1).then().statusCode(200).body("id",
				greaterThanOrEqualTo(1));
	}

	@Test
	public void test3ListarFuncionario() {
		given().contentType("application/json").when().get("/funcionarios").then().statusCode(200);
	}
	
	@Test
	public void test4DeletarFuncionario() {
		given().contentType("application/json").when().delete("/funcionarios/{id}", 2).then().statusCode(200);

	}

	@Test
	public void test5MudarFuncionario() {

		String alisson = "{\"id\" : \"1\", \"name\":\"alisson\","
				+ "\"telefone\" : \"40028922\", \"email\" : \"alisson@email.com\","
				+ "\"endereco\" : \"80\", \"dataNascimento\" : \"20 de Maio de 1998\", \"cpf\" : \"1234-5678\", \"plano\" : \"academia\", \"salario\" : \"2500\", \"cargaHoraria\" : \"50H\"}";

		given().contentType("application/json").when().delete("/funcionarios/{id}", 1).then().statusCode(200);

		given().contentType("application/json").body(alisson).when().post("/funcionarios").then().statusCode(200)
				.body("id", greaterThanOrEqualTo(1)).body("name", containsString("alisson"))
				.body("telefone", greaterThanOrEqualTo(40028922)).body("email", containsString("alisson@email.com"))
				.body("endereco", containsString("80")).body("dataNascimento", containsString("20 de Maio de 1998"))
				.body("cpf", containsString("1234-5678")).body("salario", greaterThanOrEqualTo(2500))
				.body("cargaHoraria",containsString("50H"));

	}
}
