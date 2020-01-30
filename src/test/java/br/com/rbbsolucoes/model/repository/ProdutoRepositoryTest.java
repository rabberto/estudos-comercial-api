package br.com.rbbsolucoes.model.repository;

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

import br.com.rbbsolucoes.model.entity.Grupo;
import br.com.rbbsolucoes.model.entity.Produto;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProdutoRepositoryTest {
	
	@Autowired
	ProdutoRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void devePersistirProdutoNaBaseDado() {
		//cenario
		Produto produto = criarProduto();
		
		//acao/execuccao
		Produto produtSalvo = repository.save(produto);
		
		//verificacao
		Assertions.assertThat(produtSalvo.getId()).isNotNull();
		
	}
	
	public static Produto criarProduto() {
		Grupo grupo = criarGrupo();
		return Produto.builder().nome("teste").ncm("teste").grupo(grupo).build();
	}

	public static Grupo criarGrupo() {
		return Grupo.builder().nome("Grupo").build();
	}
}
