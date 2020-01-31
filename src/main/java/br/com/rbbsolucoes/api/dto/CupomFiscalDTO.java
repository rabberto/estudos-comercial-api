package br.com.rbbsolucoes.api.dto;

import java.math.BigDecimal;
import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CupomFiscalDTO {
	
	private Long id;
	private BigDecimal valor;
	private Date emissao;
	private Long loja;
	private Long cliente;
	private Long vendedor;

}
