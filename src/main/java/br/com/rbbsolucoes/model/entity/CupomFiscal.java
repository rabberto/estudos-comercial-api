package br.com.rbbsolucoes.model.entity;

import java.math.BigDecimal;
import java.util.Date;

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
@Table(name = "CUPONS_FISCAIS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CupomFiscal {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "CODIGO_CUPOM")
	private Long id;
	
	@Column(name = "VALOR_TOTAL")
	private BigDecimal valor;
	
	@Column(name = "EMISSAO")
	private Date emissao;
	
	@ManyToOne
	@JoinColumn(name = "CODIGO_LOJA")
	private Loja loja;
	
	@ManyToOne
	@JoinColumn(name = "CODIGO_CLIENTE")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "CODIGO_VENDEDOR")
	private Vendedor vendedor;
	
	
}
