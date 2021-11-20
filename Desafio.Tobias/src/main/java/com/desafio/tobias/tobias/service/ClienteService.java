package com.desafio.tobias.tobias.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.desafio.tobias.tobias.model.ClienteModel;
import com.desafio.tobias.tobias.repository.ClienteRepository;
import com.desafio.tobias.tobias.model.ClienteLogin;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Optional<ClienteModel> cadastrarCliente(ClienteModel cliente) {

		if (clienteRepository.findByCpf(cliente.getCpf()).isPresent())
			return Optional.empty();

		cliente.setSenha(criptografarSenha(cliente.getSenha()));

		return Optional.of(clienteRepository.save(cliente));

	}

	public Optional<ClienteModel> atualizarCliente(ClienteModel cliente) {

		if (clienteRepository.findById(cliente.getIdCliente()).isPresent()) {

			Optional<ClienteModel> buscaCliente = clienteRepository.findByCpf(cliente.getCpf());

			if (buscaCliente.isPresent()) {

				if (buscaCliente.get().getIdCliente() != cliente.getIdCliente())
					return Optional.empty();

			}

			cliente.setSenha(criptografarSenha(cliente.getSenha()));

			return Optional.of(clienteRepository.save(cliente));

		}

		return Optional.empty();

	}

	public Optional<ClienteLogin> autenticarCliente(Optional<ClienteLogin> clienteLogin) {

		Optional<ClienteModel> cliente = clienteRepository.findByCpf(clienteLogin.get().getCpf());

		if (cliente.isPresent()) {

			if (compararSenhas(clienteLogin.get().getSenha(), cliente.get().getSenha())) {

				String token = gerarBasicToken(clienteLogin.get().getCpf(), clienteLogin.get().getSenha());

				clienteLogin.get().setIdCliente(cliente.get().getIdCliente());
				clienteLogin.get().setNome(cliente.get().getNome());
				clienteLogin.get().setSenha(cliente.get().getSenha());
				clienteLogin.get().setToken(token);

				return clienteLogin;

			}

		}

		return Optional.empty();

	}

	private String criptografarSenha(String senha) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.encode(senha);

	}

	private boolean compararSenhas(String senhaDigitada, String senhaBanco) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.matches(senhaDigitada, senhaBanco);

	}

	private String gerarBasicToken(String cpf, String password) {

		String tokenBase = cpf + ":" + password;
		byte[] tokenBase64 = Base64.encodeBase64(tokenBase.getBytes(Charset.forName("US-ASCII")));
		return "Basic " + new String(tokenBase64);

	}

}
