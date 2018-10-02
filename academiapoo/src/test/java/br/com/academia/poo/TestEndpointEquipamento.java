package br.com.academia.poo;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jayway.restassured.RestAssured;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // DEFINIR ORDEM DE TESTE POR NOME
public class TestEndpointEquipamento {

	private static final String SERVIDOR = "http://localhost:8080";

	public TestEndpointEquipamento() {
		RestAssured.baseURI = SERVIDOR;
	}

	@Test
	public void test1CadastrarEquipamento() {

		String Musculacao1 = "{\"id\" : \"1\", \"name\": \"supino\","
				+ "\"peso\" : \"100\", \"tipo\" : \"malhar peito\","
				+ "\"descricao\": \"O Equipamento serve para os musculos do torax\"}";
		String Musculacao2 = "{\"id\" : \"2\", \"name\":\"esteira\","
				+ "\"peso\" : \"400\", \"tipo\" : \"malhar pernas\","
				+ "\"descricao\": \"O Equipamento serve para os musculos das pernas\"}";

		given().contentType("application/json").body(Musculacao1).when().post("/equipamentos/cadastrarEquipamento")
				.then().statusCode(200).body("id", greaterThanOrEqualTo(1)).body("name", containsString("supino"))
				.body("peso", greaterThanOrEqualTo(100)).body("tipo", containsString("malhar peito"))
				.body("descricao", containsString("O Equipamento serve para os musculos do torax"));

		given().contentType("application/json").body(Musculacao2).when().post("/equipamentos/cadastrarEquipamento")
				.then().statusCode(200).body("id", greaterThanOrEqualTo(2)).body("name", containsString("esteira"))
				.body("peso", greaterThanOrEqualTo(400)).body("tipo", containsString("malhar pernas"))
				.body("descricao", containsString("Equipamento serve para os musculos das pernas"));
	}

	@Test
	public void test2PegarEquipamento() {
		given().contentType("application/json").when().get("/equipamentos/1").then().statusCode(200).body("id",
				greaterThanOrEqualTo(1));
	}

	@Test
	public void test3ListarEquipamentos() {
		given().contentType("application/json").when().get("/equipamentos/").then().statusCode(200);
	}

	@Test
	public void test4DeletarEquipamento() {
		given().contentType("application/json").when().delete("/equipamentos/{id}", 2).then().statusCode(200);

	}

	@Test
	public void test5AlterarEquipamento() {

		String Musculacao3 = "{\"id\" : \"1\", \"name\":\"barra\"," + "\"peso\" : \"50\", \"tipo\" : \"malhar peito\","
				+ "\"descricao\" : \"O Equipamento serve para os musculos do torax\"}";

		given().contentType("application/json").when().delete("/equipamentos/{id}", 1).then().statusCode(200);

		given().contentType("application/json").body(Musculacao3).when().post("/equipamentos/cadastrarEquipamento")
				.then().statusCode(200).body("id", greaterThanOrEqualTo(1)).body("name", containsString("barra"))
				.body("peso", greaterThanOrEqualTo(50)).body("tipo", containsString("malhar peito"))
				.body("descricao", containsString("O Equipamento serve para os musculos do torax"));

	}

}


