package com.desafio.tobias.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.desafio.tobias.tobias.model.ClienteModel;
import com.desafio.tobias.tobias.service.ClienteService;

public class ClienteControllerTest {

	@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	public class clienteControllerTest {

		@Autowired
		private TestRestTemplate testRestTemplate;

		@Autowired
		private ClienteService clienteService;

		@Test
		@Order(1)
		@DisplayName("Cadastrar um Cliente")
		public void deveCriarUmCliente() {

			HttpEntity<ClienteModel> requisicao = new HttpEntity<ClienteModel>(
					new ClienteModel(0L, "Juliana Pereira", "Sao Paulo", "00011122233", null));

			ResponseEntity<ClienteModel> resposta = testRestTemplate.exchange("/cliente/cadastrar", HttpMethod.POST,
					requisicao, ClienteModel.class);

			assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
			assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
			assertEquals(requisicao.getBody().getEndereco(), resposta.getBody().getEndereco());
			assertEquals(requisicao.getBody().getCpf(), resposta.getBody().getCpf());
		}

		@Test
		@Order(2)
		@DisplayName("Não deve permitir duplicação de Cliente")
		public void naoDeveDuplicarCliente() {

			clienteService.cadastrarCliente(new ClienteModel(0L, "Jose de Paula", "Belo Horizonte", "12345678910", null));

			HttpEntity<ClienteModel> requisicao = new HttpEntity<ClienteModel>(
					new ClienteModel(0L, "Jose de Paula", "Belo Horizonte", "12345678910", null));

			ResponseEntity<ClienteModel> resposta = testRestTemplate.exchange("/cliente/cadastrar", HttpMethod.POST,
					requisicao, ClienteModel.class);

			assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
		}

		@Test
		@Order(3)
		@DisplayName("Alterar dados cadastrais do Cliente")
		public void deveAtualizarUmCliente() {

			Optional<ClienteModel> clienteCreate = clienteService
					.cadastrarCliente(new ClienteModel(0L, "Maria dos Santos", "Rio de Janeiro", "98765432199", null));

			ClienteModel clienteUpdate = new ClienteModel(clienteCreate.get().getIdCliente(), "Maria dos Santos Soares",
					"Curitiba", "98765432199", null);

			HttpEntity<ClienteModel> requisicao = new HttpEntity<ClienteModel>(clienteUpdate);

			ResponseEntity<ClienteModel> resposta = testRestTemplate.withBasicAuth("root", "root")
					.exchange("/cliente/atualizar", HttpMethod.PUT, requisicao, ClienteModel.class);

			assertEquals(HttpStatus.OK, resposta.getStatusCode());
			assertEquals(clienteUpdate.getNome(), resposta.getBody().getNome());
			assertEquals(clienteUpdate.getEndereco(), resposta.getBody().getEndereco());
		}

		@Test
		@Order(4)
		@DisplayName("Listar todos os Clientes")
		public void deveMostrarTodosClientes() {

			clienteService.cadastrarCliente(new ClienteModel(0L, "Gustavo Melo", "Brasilia", "12367834500", null));

			clienteService.cadastrarCliente(new ClienteModel(0L, "Giovana Queiroz", "Salvador", "98712365400", null));

			ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root", "root").exchange("/clientes/all",
					HttpMethod.GET, null, String.class);

			assertEquals(HttpStatus.OK, resposta.getStatusCode());
		}

	}

}
