package br.com.rbbsolucoes.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDTO {

	private Long id;
	private String cpf;
	private String nome;
	
}
