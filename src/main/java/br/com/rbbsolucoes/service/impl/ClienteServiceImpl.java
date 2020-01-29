package br.com.rbbsolucoes.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.validation.OverridesAttribute;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rbbsolucoes.exception.RegraNegocioException;
import br.com.rbbsolucoes.model.entity.Cliente;
import br.com.rbbsolucoes.model.repository.ClienteRepository;
import br.com.rbbsolucoes.service.ClienteService;


@Service
public class ClienteServiceImpl implements ClienteService{
	
	private ClienteRepository repository;
	
	public ClienteServiceImpl(ClienteRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Cliente salvar(Cliente cliente) {
		validar(cliente);
		return repository.save(cliente);
	}

	@Override
	public Optional<Cliente> getById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void atualizarNome(Cliente cliente, String nome) {
		Objects.requireNonNull(cliente.getId());
		cliente.setNome(nome);
		repository.save(cliente);	
	}

	@Override
	@Transactional
	public void deletar(Cliente cliente) {
		Objects.requireNonNull(cliente.getId());
		repository.delete(cliente);		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> buscar(Cliente clienteFiltro) {
		Example example = Example.of(clienteFiltro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}
	
	@Override
	public void validar(Cliente cliente) {
		
		boolean existe = repository.existsByCpf(cliente.getCpf());
		if (existe) {
			throw new RegraNegocioException("Cliente já cadastrado para o CPF informado");
		}

		if (cliente.getCpf() == null || cliente.getCpf().trim().equals("")){
			throw new RegraNegocioException("Informe um CPF válido.");
		}
		
		if (cliente.getNome() == null || cliente.getNome().trim().equals("")) {
			throw new RegraNegocioException("Informe um Nome válido.");
		}
	}

}
