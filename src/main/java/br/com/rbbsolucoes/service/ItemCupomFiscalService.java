package br.com.rbbsolucoes.service;

import java.util.List;
import java.util.Optional;

import br.com.rbbsolucoes.model.entity.ItemCupomFiscal;

public interface ItemCupomFiscalService {
	
	ItemCupomFiscal salvar(ItemCupomFiscal itemCupomFiscal);
	
	ItemCupomFiscal atualizar(ItemCupomFiscal itemCupomFiscal);
	
	void deletar(ItemCupomFiscal itemCupomFiscal);
	
	List<ItemCupomFiscal> buscar(ItemCupomFiscal itemCupomFiscal);
		
	void validar(ItemCupomFiscal itemCupomFiscal);
	
	Optional<ItemCupomFiscal> getById(Long id);
	
}
