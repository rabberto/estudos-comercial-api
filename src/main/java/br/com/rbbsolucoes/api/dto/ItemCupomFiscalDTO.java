package br.com.rbbsolucoes.api.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemCupomFiscalDTO {

	private Long id;
	private Integer quantidade;
	private BigDecimal valorUnitario;
	private Long produto;
	private Long cupom;
	
}
