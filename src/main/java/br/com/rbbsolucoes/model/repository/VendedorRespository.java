package br.com.rbbsolucoes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rbbsolucoes.model.entity.Vendedor;

public interface VendedorRespository extends JpaRepository<Vendedor, Long>{

}
