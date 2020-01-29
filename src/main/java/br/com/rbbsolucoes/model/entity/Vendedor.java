package br.com.rbbsolucoes.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "VENDEDORES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vendedor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "CODIGO_VENDEDOR")
	private Long id;
	
	@Column(name = "MATRICULA")
	private String matricula;
	
	@Column(name = "NOME")
	private String nome;

}
