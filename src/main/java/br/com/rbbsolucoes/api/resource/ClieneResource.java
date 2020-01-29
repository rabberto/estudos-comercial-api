package br.com.rbbsolucoes.api.resource;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rbbsolucoes.api.dto.AtualizaNomeClienteDTO;
import br.com.rbbsolucoes.api.dto.ClienteDTO;
import br.com.rbbsolucoes.exception.RegraNegocioException;
import br.com.rbbsolucoes.model.entity.Cliente;
import br.com.rbbsolucoes.service.ClienteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/clientes")
@RequiredArgsConstructor
public class ClieneResource {
	
	private final ClienteService service;
	
	@PostMapping
	public ResponseEntity salvar( @RequestBody ClienteDTO dto) {
		
		Cliente cliente = Cliente.builder().nome(dto.getNome()).cpf(dto.getCpf()).build();
		
		try {
			Cliente ClienteSalvo = service.salvar(cliente);
			return new ResponseEntity(ClienteSalvo, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PutMapping("{id}/atualiza-nome")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AtualizaNomeClienteDTO dto) {
		return service.getById(id).map( entity -> {
			String nomeRecebido = dto.getNome();
			if(nomeRecebido == null || nomeRecebido.trim() == "") {
				return ResponseEntity.badRequest().body("É necessário informar o nome");
			}
			try {
				entity.setNome(nomeRecebido);
				service.atualizarNome(entity, nomeRecebido);
				return ResponseEntity.ok(entity);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			
			}).orElseGet(() -> new ResponseEntity("Cliente não encontrado para o Id informado", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.getById(id).map(entity -> {
			service.deletar(entity);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Cliente não encontrado para o Id informado", HttpStatus.BAD_REQUEST));
	}
	
	@GetMapping
	public ResponseEntity buscar( 
			@RequestParam(value = "cpf", required = false) String cpf,
			@RequestParam(value = "nome", required = false) String nome) {
		
		Cliente clienteFiltro = new Cliente();
		clienteFiltro.setNome(nome);
		clienteFiltro.setCpf(cpf);
		
		List<Cliente> clientes = service.buscar(clienteFiltro);
		return ResponseEntity.ok(clientes);
	}

}
