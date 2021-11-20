package com.desafio.tobias.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.desafio.tobias.tobias.model.ClienteModel;
import com.desafio.tobias.tobias.repository.ClienteRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteRepositoryTest {

	@Autowired
	private ClienteRepository clienteRepository;

	@BeforeAll
	void start(){
		
		clienteRepository.save(new ClienteModel(0L, "01234567890", "Tobias Gustavo da Silva Soares"));
		clienteRepository.save(new ClienteModel(0L, "12345678900", "Dafne Ariente Soares"));
	}

	@Test
	@DisplayName("Retorna 2 clientes")
	public void deveRetornarDoisClientes() {

		List<ClienteModel> listaDeClientes = clienteRepository.findAllByNomeContainingIgnoreCase("Tobias");
		assertEquals(2, listaDeClientes.size());
		assertTrue(listaDeClientes.get(0).getNome().equals("Tobias Gustavo da Silva Soares"));
		assertTrue(listaDeClientes.get(1).getNome().equals("Dafne Ariene Soares"));
	}

}
