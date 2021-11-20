package com.desafio.tobias.tobias.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.tobias.tobias.model.ClienteModel;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

	public Optional<ClienteModel> findByCpf(String cpf);

	public List<ClienteModel> findAllByNomeContainingIgnoreCase(String nome);
}
