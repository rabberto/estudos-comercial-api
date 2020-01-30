package br.com.rbbsolucoes.service;

import java.util.List;
import java.util.Optional;

import br.com.rbbsolucoes.model.entity.Vendedor;

public interface VendedorService {
	
	Vendedor salvar(Vendedor vendedor);
	
	Vendedor atualizar(Vendedor vendedor);
	
	void deletar(Vendedor vendedor);
	
	void validar(Vendedor vendedor);
	
	Optional<Vendedor> getById(Long id);
	
	List<Vendedor> buscar(Vendedor vendedorFiltro); 
}
