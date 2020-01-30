package br.com.rbbsolucoes.model.repository;

import static org.hamcrest.CoreMatchers.both;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.rbbsolucoes.model.entity.Loja;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LojaRepositoryTest {
	
	@Autowired
	LojaRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void devePersistirLojaNaBaseDados() {
		//cenario
		Loja loja = criarLoja();
		
		//acao/execucao
		Loja lojaSalva = repository.save(loja);
		
		//verificacao
		Assertions.assertThat(lojaSalva.getId()).isNotNull();
	}
	
	public static Loja criarLoja() {
		return Loja.builder().nome("Loja 1").build();
	}


}

