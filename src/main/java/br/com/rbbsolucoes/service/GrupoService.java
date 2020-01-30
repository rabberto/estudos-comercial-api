package br.com.rbbsolucoes.service;

import java.util.List;
import java.util.Optional;

import br.com.rbbsolucoes.model.entity.Grupo;

public interface GrupoService {
	
	Grupo salvar(Grupo grupo);
	
	void atualizarNome(Grupo grupo, String nome);
	
	void deletar(Grupo grupo);
	
	void validar(Grupo grupo);
	
	Optional<Grupo> getById(Long id);
	
	List<Grupo> buscar(Grupo grupoFiltro);

}
