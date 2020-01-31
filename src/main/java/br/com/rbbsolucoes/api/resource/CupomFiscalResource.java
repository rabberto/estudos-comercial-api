package br.com.rbbsolucoes.api.resource;

import java.math.BigDecimal;
import java.sql.Date;
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

import br.com.rbbsolucoes.api.dto.CupomFiscalDTO;
import br.com.rbbsolucoes.exception.RegraNegocioException;
import br.com.rbbsolucoes.model.entity.Cliente;
import br.com.rbbsolucoes.model.entity.CupomFiscal;
import br.com.rbbsolucoes.model.entity.Loja;
import br.com.rbbsolucoes.model.entity.Vendedor;
import br.com.rbbsolucoes.service.ClienteService;
import br.com.rbbsolucoes.service.CupomFiscalService;
import br.com.rbbsolucoes.service.LojaService;
import br.com.rbbsolucoes.service.VendedorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cupons-fiscais")
@RequiredArgsConstructor
public class CupomFiscalResource {
	
	private final CupomFiscalService service;
	private final VendedorService vendedorService;
	private final ClienteService clienteService;
	private final LojaService lojaService;
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody CupomFiscalDTO dto) {
		try {
			CupomFiscal cupomFiscal = converter(dto);
			cupomFiscal = service.salvar(cupomFiscal);
			return new ResponseEntity(cupomFiscal, HttpStatus.CREATED);
 		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
		
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CupomFiscalDTO dto) {
		return service.getById(id).map(entity -> {
			try {
				CupomFiscal cupomFiscal = converter(dto);
				cupomFiscal.setId(entity.getId());
				service.atualizar(cupomFiscal);
				return ResponseEntity.ok(cupomFiscal);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Cupom Fiscal não encontrado para o Id informado.", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.getById(id).map(entity -> {
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Cupom Fiscal não encontrado para o Id informado.", HttpStatus.BAD_REQUEST));
	}
	
	@GetMapping
	private ResponseEntity buscar(
			
			@RequestParam(value = "emissao", required = false) Date emissao,
			@RequestParam(value = "cliente", required = false) Long idCliente,
			@RequestParam(value = "vendedor", required = false) Long idVendedor,
			@RequestParam(value = "loja", required = false) Long idLoja,
			@RequestParam(value = "valor", required = false) BigDecimal valor) {
		
		CupomFiscal cupomFiscalFiltro = new CupomFiscal();
		
		if (idCliente != null) {
			Optional<Cliente> cliente = clienteService.getById(idCliente);
			if (!cliente.isPresent()) {
				return ResponseEntity.badRequest().body("Id do Cliente informador não cadastrado na Base de Dados.");
			} else {
				cupomFiscalFiltro.setCliente(cliente.get());
			}
		}
		
		if (idVendedor != null) {
			Optional<Vendedor> vendedor = vendedorService.getById(idVendedor);
			if (!vendedor.isPresent()) {
				return ResponseEntity.badRequest().body("Id do Vendedor informado não cadastrado na Base de Dados.");
			} else {
				cupomFiscalFiltro.setVendedor(vendedor.get());
			}
		}
		
		if (idLoja != null) {
			Optional<Loja> loja = lojaService.getById(idLoja);
			if (!loja.isPresent()) {
				return ResponseEntity.badRequest().body("Id da Loja informada não cadastrada na Base de Dados.");
			} else {
				cupomFiscalFiltro.setLoja(loja.get());
			}
		}
	
		cupomFiscalFiltro.setEmissao(emissao);
		cupomFiscalFiltro.setValor(valor);
		
		List<CupomFiscal> CuponsFiscais = service.buscar(cupomFiscalFiltro);
		return ResponseEntity.ok(CuponsFiscais);
		
	}
	
	private CupomFiscal converter(CupomFiscalDTO dto) {	
		
		Vendedor vendedor = vendedorService.getById(dto.getVendedor())
				.orElseThrow(() -> new RegraNegocioException("Vendedor não encontrado para o Id informado."));
		
		Cliente cliente = clienteService.getById(dto.getCliente())
				.orElseThrow(() -> new RegraNegocioException("Cliente não encontrado para o Id informado."));
	
		Loja loja = lojaService.getById(dto.getLoja())
				.orElseThrow(() -> new RegraNegocioException("Loja não encontrada para o Id informado."));
		
		CupomFiscal cupomFiscal = new CupomFiscal();
		cupomFiscal.setVendedor(vendedor);
		cupomFiscal.setCliente(cliente);
		cupomFiscal.setLoja(loja);
		cupomFiscal.setId(dto.getId());
		cupomFiscal.setEmissao(dto.getEmissao());
		cupomFiscal.setValor(dto.getValor());
	
		return cupomFiscal;
	}
	
}
