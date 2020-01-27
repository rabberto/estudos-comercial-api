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
@Table(name = "LOJAS", schema = "comercial")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loja {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "CODIGO_LOJA")
	private Long id;
	
	@Column(name = "NOME_LOJA")
	private String nome;
	

}
