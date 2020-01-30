package br.com.rbbsolucoes.service;

import java.util.List;
import java.util.Optional;

import br.com.rbbsolucoes.model.entity.Loja;

public interface LojaService {
	
	Loja salvar(Loja loja);
	
	void atualizarNome(Loja loja, String nome);
	
	void deletar(Loja loja);
	
	void validar(Loja loja);
	
	Optional<Loja> getById(Long id);
	
	List<Loja> buscar(Loja lojaFiltro);

}
