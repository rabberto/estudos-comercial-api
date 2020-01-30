package br.com.rbbsolucoes.api.resource;

import java.util.List;

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

import br.com.rbbsolucoes.api.dto.LojaAtualizaNomeDTO;
import br.com.rbbsolucoes.api.dto.LojaDTO;
import br.com.rbbsolucoes.exception.RegraNegocioException;
import br.com.rbbsolucoes.model.entity.Loja;
import br.com.rbbsolucoes.service.LojaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/lojas")
@RequiredArgsConstructor
public class LojaResource {

	private final LojaService service;
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody LojaDTO dto) {
		Loja loja = Loja.builder().nome(dto.getNome()).build();
		try {
			Loja lojaSalva = service.salvar(loja);
			return new ResponseEntity(lojaSalva, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}/atualiza-nome")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LojaAtualizaNomeDTO dto) {
		return service.getById(id).map( entity -> {
		String nomeRecebido = dto.getNome();
		if(nomeRecebido == null || nomeRecebido.trim().equals("")) {
			return ResponseEntity.badRequest().body("É necessário informar o Nome da Loja");
		}
		try {
			entity.setNome(nomeRecebido);
			service.atualizarNome(entity, nomeRecebido);
			return ResponseEntity.ok(entity);			
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		}).orElseGet(() -> new ResponseEntity("Loja não encontrada para o Id informado.", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.getById(id).map( entity -> {
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Loja não encontrada para o Id informado", HttpStatus.BAD_REQUEST));
	}
	
	@GetMapping
	public ResponseEntity buscar(@RequestParam(value = "nome", required = false) String nome) {
		
		Loja lojaFiltro = new Loja();
		lojaFiltro.setNome(nome);
		
		List<Loja> lojas = service.buscar(lojaFiltro);
		return ResponseEntity.ok(lojas);
	}
	
}
