package br.com.rbbsolucoes.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUTOS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "CODIGO_PRODUTO")
	private Long id;
	
	@Column(name = "NCM")
	private String ncm;
	
	@Column(name = "DESCRICAO")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "CODIGO_GRUPO")
	private Grupo grupo;
	
}
