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
import br.com.rbbsolucoes.model.entity.Vendedor;
import br.com.rbbsolucoes.model.repository.VendedorRepository;
import br.com.rbbsolucoes.service.VendedorService;

@Service
public class VendedorServiceImpl implements VendedorService{
	
	VendedorRepository repository;
	
	public VendedorServiceImpl(VendedorRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Vendedor salvar(Vendedor vendedor) {
		validar(vendedor);
		return repository.save(vendedor);
	}

	@Override
	@Transactional
	public Vendedor atualizar(Vendedor vendedor) {
		Objects.requireNonNull(vendedor.getId());
		validar(vendedor);
		return repository.save(vendedor);
	}
	

	@Override
	@Transactional
	public void deletar(Vendedor vendedor) {
		Objects.requireNonNull(vendedor.getId());
		repository.delete(vendedor);
	}

	@Override
	public void validar(Vendedor vendedor) {
		if (vendedor.getNome() == null || vendedor.getNome().trim().equals("")) {
			throw new RegraNegocioException("Nome do Vendendor é obrigatório.");
		}
		
		if (vendedor.getMatricula() == null || vendedor.getMatricula().trim().equals("")) {
			throw new RegraNegocioException("Número da Matrícula do Vendendor é obrigatório.");
		}
		
	}

	@Override
	public Optional<Vendedor> getById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<Vendedor> buscar(Vendedor vendedorFiltro) {
		Example example = Example.of(vendedorFiltro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}


	

}
