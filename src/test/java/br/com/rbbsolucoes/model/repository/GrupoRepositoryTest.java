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

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class GrupoRepositoryTest {
	
	@Autowired
	GrupoRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void devePersistirGrupoNaBaseDados() {
		//cenario
		Grupo grupo = criarGrupo();
		
		//acao/execucao
		Grupo grupoSalvo = repository.save(grupo);
		
		//verificacao
		Assertions.assertThat(grupoSalvo.getId()).isNotNull();
	}
	
	public static Grupo criarGrupo() {
		return Grupo.builder().nome("Teste").build();
	}

}
