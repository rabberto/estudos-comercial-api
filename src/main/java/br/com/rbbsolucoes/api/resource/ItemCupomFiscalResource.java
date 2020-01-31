package br.com.rbbsolucoes.api.resource;

import java.util.List;
import java.util.Optional;

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

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import br.com.rbbsolucoes.api.dto.ItemCupomFiscalDTO;
import br.com.rbbsolucoes.exception.RegraNegocioException;
import br.com.rbbsolucoes.model.entity.CupomFiscal;
import br.com.rbbsolucoes.model.entity.ItemCupomFiscal;
import br.com.rbbsolucoes.model.entity.Produto;
import br.com.rbbsolucoes.service.CupomFiscalService;
import br.com.rbbsolucoes.service.ItemCupomFiscalService;
import br.com.rbbsolucoes.service.ProdutoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/itens-cupons-fiscais")
@RequiredArgsConstructor
public class ItemCupomFiscalResource {

	private final ItemCupomFiscalService service;
	private final CupomFiscalService cupomFiscalService;
	private final ProdutoService produtoService;
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody ItemCupomFiscalDTO dto) {
		try {
			ItemCupomFiscal itemCupomFiscal = converter(dto);
			itemCupomFiscal = service.salvar(itemCupomFiscal);
			return new ResponseEntity(itemCupomFiscal, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ItemCupomFiscalDTO dto) {
		return service.getById(id).map(entity -> {
		
		try {
			ItemCupomFiscal itemCupomFiscal = converter(dto);
			itemCupomFiscal.setId(entity.getId());
			service.atualizar(itemCupomFiscal);
			return ResponseEntity.ok(itemCupomFiscal);
			
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		}).orElseGet(() -> new ResponseEntity("Item Cupom Fiscal não encontrado para o Id informado.", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.getById(id).map(entity -> {
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Item Cupom Fiscal não encontrado para o Id informado.", HttpStatus.BAD_REQUEST));
	}
	
	@GetMapping
	public ResponseEntity buscar(
			@RequestParam(value = "cupom") Long idCupom,
			@RequestParam(value = "produto", required = false) Long idProduto) {
		
		ItemCupomFiscal itemCupomFiscalFiltro = new ItemCupomFiscal();
		
		Optional<CupomFiscal> cupomFiscal = cupomFiscalService.getById(idCupom);
		if (!cupomFiscal.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Cupom Fiscal não encontrado para o Id informado.");
		}else {
			itemCupomFiscalFiltro.setCupomFiscal(cupomFiscal.get());
		}
		
		if (idProduto != null) {
			Optional<Produto> produto = produtoService.getById(idProduto);
			if (!produto.isPresent()) {
				return ResponseEntity.badRequest().body("Id do Produto informado não encontrado na Base de Dados.");
			} else {
				itemCupomFiscalFiltro.setProduto(produto.get());
			}
		}
		List<ItemCupomFiscal> itemCupomFiscals = service.buscar(itemCupomFiscalFiltro);
		return ResponseEntity.ok(itemCupomFiscals);
	}

	private ItemCupomFiscal converter(ItemCupomFiscalDTO dto) {
		
		Produto produto = produtoService.getById(dto.getProduto())
				.orElseThrow(() -> new RegraNegocioException("Produto não encontrado para o Id informado"));
		
		CupomFiscal cupoFiscal = cupomFiscalService.getById(dto.getCupom())
				.orElseThrow(() -> new RegraNegocioException("Cupom não encontrador para o Id informado"));
		
		ItemCupomFiscal itemCupomFiscal = new ItemCupomFiscal();
		itemCupomFiscal.setId(dto.getId());
		itemCupomFiscal.setCupomFiscal(cupoFiscal);
		itemCupomFiscal.setProduto(produto);
		itemCupomFiscal.setQuantidade(dto.getQuantidade());
		itemCupomFiscal.setValorUnitario(dto.getValorUnitario());
		
		return itemCupomFiscal;
	}
}
