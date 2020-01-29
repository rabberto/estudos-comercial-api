package br.com.rbbsolucoes.model.entity;

import java.math.BigDecimal;

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
@Table(name = "ITENS_CUPOM_FISCAL")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemCupomFiscal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "CODIGO_ITEM_CUPOM_FISCAL")
	private Long id;
	
	@Column(name = "QUANTIDADE")
	private Integer quantidade;
	
	@Column(name = "VALOR_UNITARIO")
	private BigDecimal valorUnitario;
	
	@ManyToOne
	@JoinColumn(name = "CODIGO_PRODUTO")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "CODIGO_CUPOM")
	private CupomFiscal cupomFiscal;

}


