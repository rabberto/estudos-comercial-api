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
import br.com.rbbsolucoes.model.entity.Produto;
import br.com.rbbsolucoes.model.repository.ProdutoRepository;
import br.com.rbbsolucoes.service.ProdutoService;

@Service
public class ProdutoServiceImp implements ProdutoService {
	
	private ProdutoRepository repository;
	
	public ProdutoServiceImp(ProdutoRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Produto salvar(Produto produto) {
		validar(produto);
		return repository.save(produto);
	}

	@Override
	@Transactional
	public Produto atualizar(Produto produto) {
		Objects.requireNonNull(produto.getId());
		validar(produto);
		return repository.save(produto);
	}

	@Override
	@Transactional
	public void deletar(Produto produto) {
		Objects.requireNonNull(produto.getId());
		repository.delete(produto);		
	}

	@Override
	public List<Produto> buscar(Produto produtoFiltro) {
		Example example = Example.of(produtoFiltro, ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));  
		return repository.findAll(example);
	}

	@Override
	public void validar(Produto produto) {
		if(produto.getDescricao() == null || produto.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Descrição do Produto é obrigatória.");
		}
		if(produto.getNcm() == null || produto.getNcm().trim().equals("")) {
			throw new RegraNegocioException("NCM do Produto é obrigatória.");
		}
		if(produto.getGrupo() == null || produto.getGrupo().getId() == null) {
			throw new RegraNegocioException("NCM do Produto é obrigatória.");
		}		
	}

	@Override
	public Optional<Produto> getById(Long id) {
		return repository.findById(id);
	}
	
}
