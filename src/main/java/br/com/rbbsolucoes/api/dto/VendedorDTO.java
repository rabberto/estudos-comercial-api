package br.com.rbbsolucoes.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendedorDTO {
	
	private Long id;
	private String nome;
	private String matricula;

}
