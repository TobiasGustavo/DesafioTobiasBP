package com.desafio.tobias.tobias.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desafio.tobias.tobias.model.ClienteLogin;
import com.desafio.tobias.tobias.model.ClienteModel;
import com.desafio.tobias.tobias.repository.ClienteRepository;
import com.desafio.tobias.tobias.service.ClienteService;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ClienteRepository clienteRepository;

	@GetMapping("/all")
	public ResponseEntity<List<ClienteModel>> getAll() {

		return ResponseEntity.ok(clienteRepository.findAll());

	}

	@PostMapping("/logar")
	public ResponseEntity<ClienteLogin> clientelogin(@RequestBody Optional<ClienteLogin> clienteLogin) {

		return clienteService.autenticarCliente(clienteLogin)
				.map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

	}

	@PostMapping("/cadastrar")
	public ResponseEntity<ClienteModel> postCliente(@Valid @RequestBody ClienteModel cliente) {

		return clienteService.cadastrarCliente(cliente)
				.map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

	}

	@PutMapping("/atualizar")
	public ResponseEntity<ClienteModel> putCliente(@Valid @RequestBody ClienteModel cliente) {
		return clienteService.atualizarCliente(cliente)
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

	}

}
