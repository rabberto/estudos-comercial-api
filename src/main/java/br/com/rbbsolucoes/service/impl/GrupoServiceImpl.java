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
import br.com.rbbsolucoes.model.entity.Grupo;
import br.com.rbbsolucoes.model.repository.GrupoRepository;
import br.com.rbbsolucoes.service.GrupoService;

@Service
public class GrupoServiceImpl implements GrupoService{
	
	GrupoRepository repository;
	
	public GrupoServiceImpl(GrupoRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Grupo salvar(Grupo grupo) {
		validar(grupo);
		return repository.save(grupo);
	}

	@Override
	public void atualizarNome(Grupo grupo, String nome) {
		Objects.requireNonNull(grupo.getId());
		grupo.setNome(nome);
		repository.save(grupo);
		
	}

	@Override
	@Transactional
	public void deletar(Grupo grupo) {
		Objects.requireNonNull(grupo.getId());
		repository.delete(grupo);
		
	}

	@Override
	public void validar(Grupo grupo) {
		if(grupo.getNome() == null || grupo.getNome().trim().equals("")) {
			throw new RegraNegocioException("Nome do Grupo é obrigatório");
		}
		
	}

	@Override
	public Optional<Grupo> getById(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<Grupo> buscar(Grupo grupoFiltro) {
		Example example = Example.of(grupoFiltro,
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

}
