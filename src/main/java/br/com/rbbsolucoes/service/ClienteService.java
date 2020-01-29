package br.com.rbbsolucoes.service;

import java.util.List;
import java.util.Optional;

import br.com.rbbsolucoes.model.entity.Cliente;

public interface ClienteService {
	
	Cliente salvar(Cliente cliente);
	
	void atualizarNome(Cliente cliente, String nome);
	
	void deletar(Cliente cliente);
			
	void validar(Cliente cliente);
	
	Optional<Cliente> getById(Long id);
	
	List<Cliente> buscar(Cliente clienteFiltro);
			
}
