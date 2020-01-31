package br.com.rbbsolucoes.service;

import java.util.List;
import java.util.Optional;

import br.com.rbbsolucoes.model.entity.CupomFiscal;

public interface CupomFiscalService {
	
	CupomFiscal salvar(CupomFiscal cupomFiscal);
	
	CupomFiscal atualizar(CupomFiscal cupomFiscal);
		
	void deletar(CupomFiscal cupomFiscal);
	
	List<CupomFiscal> buscar(CupomFiscal cupomFiscal);
	
	void validar(CupomFiscal cupomFiscal);
	
	Optional<CupomFiscal> getById(Long id);

}
