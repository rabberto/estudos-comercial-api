package br.com.rbbsolucoes.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rbbsolucoes.api.resource.ItemCupomFiscalResource;
import br.com.rbbsolucoes.exception.RegraNegocioException;
import br.com.rbbsolucoes.model.entity.CupomFiscal;
import br.com.rbbsolucoes.model.entity.ItemCupomFiscal;
import br.com.rbbsolucoes.model.repository.CupomFiscalRepository;
import br.com.rbbsolucoes.service.CupomFiscalService;
import br.com.rbbsolucoes.service.ItemCupomFiscalService;

@Service
public class CupomFiscalServiceImpl implements CupomFiscalService{
	
	private CupomFiscalRepository repository;
	
	public CupomFiscalServiceImpl(CupomFiscalRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public CupomFiscal salvar(CupomFiscal cupomFiscal) {
		validar(cupomFiscal);
		return repository.save(cupomFiscal);
	}

	@Override
	@Transactional
	public CupomFiscal atualizar(CupomFiscal cupomFiscal) {
		Objects.requireNonNull(cupomFiscal.getId());
		validar(cupomFiscal);
		return repository.save(cupomFiscal);
	}

	@Override
	@Transactional
	public void deletar(CupomFiscal cupomFiscal) {
		Objects.requireNonNull(cupomFiscal.getId());
		repository.delete(cupomFiscal);		
	}

	@Override
	@Transactional(readOnly = true)
	public List<CupomFiscal> buscar(CupomFiscal cupomFiscal) {
		Example  example = Example.of(cupomFiscal, ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Override
	public void validar(CupomFiscal cupomFiscal) {

		if (cupomFiscal.getCliente() == null || cupomFiscal.getCliente().getId() == null) {
			throw new RegraNegocioException("Informe o Cliente do Cupom Fiscal.");
		}
		if (cupomFiscal.getLoja() == null || cupomFiscal.getLoja().getId() == null) {
			throw new RegraNegocioException("Informe a Loja do Cupom Fiscal.");
		}
		if (cupomFiscal.getVendedor() == null || cupomFiscal.getVendedor() == null) {
			throw new RegraNegocioException("Informe o Vendedor do Cupom Fiscal.");
		}
		if (cupomFiscal.getEmissao() == null) {
			throw new RegraNegocioException("Informe a Data de Emissão do Cupom Fiscal.");
		}
		if (cupomFiscal.getValor() == null || cupomFiscal.getValor().compareTo(BigDecimal.ZERO) < 1) {
			throw new RegraNegocioException("Informe o Valor do Cupom Fiscal.");
		}
		
	}
		
	@Override
	public Optional<CupomFiscal> getById(Long id) {
		return repository.findById(id);
	}

}
