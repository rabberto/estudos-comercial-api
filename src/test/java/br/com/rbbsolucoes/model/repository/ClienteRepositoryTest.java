package br.com.rbbsolucoes.model.repository;


import java.util.Optional;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.rbbsolucoes.model.entity.Cliente;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ClienteRepositoryTest {
	
	@Autowired
	ClienteRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveRetornarTrueQuandoCpfJaCadastrado() {
		//cenario
		Cliente cliente = criarCliente();
		entityManager.persist(cliente);
		
		//acoa/execucao
		boolean result = repository.existsByCpf("31829869884");
		
		//verificacao
		Assertions.assertThat(result).isTrue();
		
	}
	
	public void deveRetornarFalseQuandoCpfJaCadastrado() {
		//cenario
		Cliente cliente = criarCliente();
		entityManager.persist(cliente);
		
		//acao/execucao
		boolean result = repository.existsByCpf("31829869884");
		
		//verificacao
		Assertions.assertThat(result).isFalse();
		
	}
	
	public void devePersistirClienteNaBaseDados() {
		//cenario
		Cliente cliente = criarCliente();
		
		//acao/execucao
		Cliente clienteSalvo = repository.save(cliente);
		
		//verficicao
		Assertions.assertThat(clienteSalvo.getId()).isNotNull();
	}
	
	public void deveBuscarClientePorCpf() {
		//cenario
		Cliente cliente = criarCliente();
		entityManager.persist(cliente);
		
		//verificao
		Optional<Cliente> result = repository.findByCpf("31829869884");
		
		Assertions.assertThat(result.isPresent()).isTrue();
	}
	
	public void deveRetornarVazioQuandoClientePorCpfNaoEstiverCadastrado() {
		//cenario
		Cliente cliente = criarCliente();
		entityManager.persist(cliente);
		
		//verificao
		Optional<Cliente> result = repository.findByCpf("31829869884");
		
		Assertions.assertThat(result.isPresent()).isTrue();
	}
	
	public static Cliente criarCliente() {
		return Cliente.builder().cpf("31829869884").nome("Teste").build();
	}
}
