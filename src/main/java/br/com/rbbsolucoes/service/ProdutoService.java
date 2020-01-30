package br.com.rbbsolucoes.service;

import java.util.List;
import java.util.Optional;

import br.com.rbbsolucoes.model.entity.Produto;

public interface ProdutoService {

	Produto salvar(Produto produto);
	
	Produto atualizar(Produto produto);
	
	void deletar(Produto produto);
	
	List<Produto> buscar(Produto produtoFiltro);
	
	void validar(Produto produto);
	
	Optional<Produto> getById(Long id);
	
}
