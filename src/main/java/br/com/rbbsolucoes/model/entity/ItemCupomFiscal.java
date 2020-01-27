package br.com.rbbsolucoes.model.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ItemCupomFiscal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "CODIGO_ITEM_CUPOM_FISCAL")
	private Long id;
	
	@Column(name = "QUANTIDADE")
	private Integer quantidade;
	
	@Column(name = "VALOR_UNITARIO")
	private BigDecimal valorUnitario;
	
	@JoinColumn(name = "CODIGO_PRODUTO")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "CODIGO_CUPOM")
	private CupomFiscal cumpoFiscal;

}


