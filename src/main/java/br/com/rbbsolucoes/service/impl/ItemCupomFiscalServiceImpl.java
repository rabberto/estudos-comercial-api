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

import br.com.rbbsolucoes.exception.RegraNegocioException;
import br.com.rbbsolucoes.model.entity.ItemCupomFiscal;
import br.com.rbbsolucoes.model.repository.ItemCupomFiscalRepository;
import br.com.rbbsolucoes.service.ItemCupomFiscalService;

@Service
public class ItemCupomFiscalServiceImpl implements ItemCupomFiscalService{

	private static final Object itemCupomFiscalFiltro = null;
	ItemCupomFiscalRepository repository;
	
	public ItemCupomFiscalServiceImpl(ItemCupomFiscalRepository repository) {
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public ItemCupomFiscal salvar(ItemCupomFiscal itemCupomFiscal) {
		validar(itemCupomFiscal);
		return repository.save(itemCupomFiscal);
	}

	@Override
	@Transactional
	public ItemCupomFiscal atualizar(ItemCupomFiscal itemCupomFiscal) {
		Objects.requireNonNull(itemCupomFiscal.getId());
		validar(itemCupomFiscal);
		return repository.save(itemCupomFiscal);
	}

	@Override
	@Transactional
	public void deletar(ItemCupomFiscal itemCupomFiscal) {
		Objects.requireNonNull(itemCupomFiscal.getId());
		repository.delete(itemCupomFiscal);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemCupomFiscal> buscar(ItemCupomFiscal itemCupomFiscalFiltro) {
		Example example = Example.of(itemCupomFiscalFiltro, ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}
	
	@Override
	public void validar(ItemCupomFiscal itemCupomFiscal) {
		if (itemCupomFiscal.getProduto() == null || itemCupomFiscal.getProduto().getId() == null) {
			throw new RegraNegocioException("Informe o Id do Produto.");
		}
		if (itemCupomFiscal.getQuantidade() == null || itemCupomFiscal.getQuantidade().equals(0)) {
			throw new RegraNegocioException("Informe a Quantidade válida.");
		}
		if (itemCupomFiscal.getValorUnitario() == null || itemCupomFiscal.getValorUnitario().compareTo(BigDecimal.ZERO) < 1) {
			throw new RegraNegocioException("Informe o Valo Unitário válido.");
		}
		if (itemCupomFiscal.getCupomFiscal() == null || itemCupomFiscal.getCupomFiscal().getId() == null) {
			throw new RegraNegocioException("Informe o Id do Cupom Fiscal.");
		}
	}
	
	@Override
	public Optional<ItemCupomFiscal> getById(Long id) {
		return repository.findById(id);
	}
}
