package br.com.rbbsolucoes.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rbbsolucoes.model.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	boolean existsByCpf(String cpf);
	
	Optional<Cliente> findByCpf(String cpf);
	
		
}
