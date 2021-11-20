package com.desafio.tobias.tobias.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "tb_cliente")
public class ClienteModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idCliente;

	@NotBlank(message = "Ops! O campo nome NÃO pode ficar em branco.")
	@Size(min = 3, max = 255, message = "O campo nome tem no mínimo 3 caracteres  e no máximo 255.")
	private String nome;

	@NotBlank(message = "Ops! O campo endereco NÃO pode ficar em branco.")
	@Size(max = 255, message = "O campo endereco tem no máximo 255.")
	private String endereco;

	@ApiModelProperty
	@NotBlank(message = "Ops! O campo cpf NÃO pode ficar em branco.")
	@Size(min = 11, max = 11, message = "O campo cpf tem no mínimo 11 caracteres  e no máximo 11.")
	private String cpf;

	@NotBlank(message = "Ops! O campo senha NÃO pode ficar em branco.")
	@Size(min = 8, message = "O campo senha tem no mínimo 8.")
	private String senha;

	public ClienteModel(long idCliente, String nome, String endereco, String cpf, String senha) {

		this.idCliente = idCliente;
		this.nome = nome;
		this.endereco = endereco;
		this.cpf = cpf;
		this.senha = senha;
	}

	public ClienteModel(long idCliente,  String cpf, String nome) { // Teste JUnit 

	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
