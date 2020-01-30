package br.com.rbbsolucoes.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rbbsolucoes.exception.RegraNegocioException;
import br.com.rbbsolucoes.model.entity.Loja;
import br.com.rbbsolucoes.model.repository.LojaRepository;
import br.com.rbbsolucoes.service.LojaService;

@Service
public class LojaServiceImpl implements LojaService{
	
	LojaRepository repository;
	
	public LojaServiceImpl(LojaRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Loja salvar(Loja loja) {
		validar(loja);
		return repository.save(loja);
	}

	@Override
	public void atualizarNome(Loja loja, String nome) {
		Objects.requireNonNull(loja.getId());
		loja.setNome(nome);
		repository.save(loja);		
	}

	@Override
	@Transactional
	public void deletar(Loja loja) {
		Objects.requireNonNull(loja.getId());
		repository.delete(loja);		
	}

	@Override
	public void validar(Loja loja) {
		if(loja.getNome() == null || loja.getNome().trim().equals("")) {
			throw new RegraNegocioException("Nome da Loja é obrigatória.");
		}
		
	}

	@Override
	public Optional<Loja> getById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<Loja> buscar(Loja lojaFiltro) {
		Example example = Example.of(lojaFiltro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

}
