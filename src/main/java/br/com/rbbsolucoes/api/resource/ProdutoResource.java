package br.com.rbbsolucoes.api.resource;

import java.util.List;
import java.util.Optional;

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

import br.com.rbbsolucoes.api.dto.ProdutoDTO;
import br.com.rbbsolucoes.exception.RegraNegocioException;
import br.com.rbbsolucoes.model.entity.Grupo;
import br.com.rbbsolucoes.model.entity.Produto;
import br.com.rbbsolucoes.service.GrupoService;
import br.com.rbbsolucoes.service.ProdutoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoResource {
	
	private final ProdutoService service;
	private final GrupoService grupoService;
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody ProdutoDTO dto) {
		try {
			Produto entity = converter(dto);
			entity = service.salvar(entity);
			return new ResponseEntity(entity, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ProdutoDTO dto) {
		return service.getById(id).map(entity -> {
			try {
				Produto produto = converter(dto);
				produto.setId(entity.getId());
				service.atualizar(produto);
				return ResponseEntity.ok(produto);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			
		}).orElseGet(() -> new ResponseEntity("Produto não encontrado para o Id informado.", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	private ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.getById(id).map(entity -> {
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity<>("Produto não encontrado para o Id informado.", HttpStatus.BAD_REQUEST));
	}
	
	@GetMapping
	public ResponseEntity buscar(
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "ncm", required = false) String ncm,
			@RequestParam(value = "grupo", required = false) Long idGrupo) {
		
		Produto produtoFiltro = new Produto();
		produtoFiltro.setDescricao(descricao);
		produtoFiltro.setNcm(ncm);
		
		if(idGrupo != null) {			
			Optional<Grupo> grupo = grupoService.getById(idGrupo);
			if(!grupo.isPresent()) {
				return ResponseEntity.badRequest().body("Id do Grupo informado não cadastrado na Base de Dados.");
			}else {
				produtoFiltro.setGrupo(grupo.get());
			}			
		}
		
		List<Produto> produtos = service.buscar(produtoFiltro);
		return ResponseEntity.ok(produtos);
		
	}
	
	private Produto converter(ProdutoDTO dto) {
		
		Grupo grupo =  grupoService.getById(dto.getGrupo()).orElseThrow(() -> new RegraNegocioException("Grupo não encontrado para o Id informado."));
		
		Produto produto = new Produto();
		produto.setDescricao(dto.getDescricao());
		produto.setNcm(dto.getNcm());
		produto.setGrupo(grupo);
		return produto;
	}
	

}
