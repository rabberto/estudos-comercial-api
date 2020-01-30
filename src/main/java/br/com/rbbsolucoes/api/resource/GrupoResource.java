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

import br.com.rbbsolucoes.api.dto.GrupoAtualizaNomeDTO;
import br.com.rbbsolucoes.api.dto.GrupoDTO;
import br.com.rbbsolucoes.exception.RegraNegocioException;
import br.com.rbbsolucoes.model.entity.Grupo;
import br.com.rbbsolucoes.service.GrupoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/grupos")
@RequiredArgsConstructor
public class GrupoResource {
	
	private final GrupoService service;
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody GrupoDTO dto) {
		Grupo grupo = Grupo.builder().nome(dto.getNome()).build();
		
		try {
			Grupo grupoSalvo = service.salvar(grupo);
			return new ResponseEntity(grupoSalvo, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}/atualiza-nome")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody GrupoAtualizaNomeDTO dto) {
		return service.getById(id).map( entity -> {
			String nomeRecebido = dto.getNome();
			if(nomeRecebido == null || nomeRecebido.trim().equals("")) {
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
		return service.getById(id).map( entity -> {
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Grupo não encontrado para o Id informado", HttpStatus.BAD_REQUEST));
	}

	@GetMapping
	public ResponseEntity buscar(@RequestParam(value = "nome", required = false) String nome) {
		
		Grupo grupoFiltro = new Grupo();
		grupoFiltro.setNome(nome);
		
		List<Grupo> grupos = service.buscar(grupoFiltro);
		return ResponseEntity.ok(grupos);
	}
}

	
