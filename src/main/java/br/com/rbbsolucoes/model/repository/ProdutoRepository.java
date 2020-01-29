package br.com.rbbsolucoes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rbbsolucoes.model.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
