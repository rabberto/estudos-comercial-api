package br.com.rbbsolucoes.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.rbbsolucoes.model.entity.Grupo;
import br.com.rbbsolucoes.model.entity.Vendedor;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class VendedorRepositoryTest {
	
	@Autowired
	VendedorRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void devePersistirVendendorNaBaseDado() {
		//cenario
		Vendedor vendedor = criarVendedor();
		
		//acao/execucao
		Vendedor vendedorSalvo = repository.save(vendedor);
		
		//verificacao
		Assertions.assertThat(vendedorSalvo.getId()).isNotNull();
		
	}
	
	public static Vendedor criarVendedor() {
		return Vendedor.builder().nome("RAFAEL").matricula("123456").build();
	}

}
