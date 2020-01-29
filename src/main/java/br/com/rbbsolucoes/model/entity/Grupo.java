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
@Table(name = "GRUPOS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Grupo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "CODIGO_GRUPO")
	private Long id;
	
	@Column(name = "DESCRICAO_GRUPO")
	private String nome;
}
