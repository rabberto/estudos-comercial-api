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

import br.com.rbbsolucoes.api.dto.VendedorDTO;
import br.com.rbbsolucoes.exception.RegraNegocioException;
import br.com.rbbsolucoes.model.entity.Vendedor;
import br.com.rbbsolucoes.service.VendedorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/vendedores")
@RequiredArgsConstructor
public class VendendorResource {
	
	private final VendedorService service;
	
	@PostMapping
	public ResponseEntity salvar(@RequestBody VendedorDTO dto) {
		try {
			Vendedor vendedor = converter(dto);
			Vendedor vendedorSalvo = service.salvar(vendedor);
			return new ResponseEntity(vendedorSalvo, HttpStatus.CREATED);			
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody VendedorDTO dto) {
		return service.getById(id).map( entity -> {
			try {
				Vendedor vendedor = converter(dto);
				vendedor.setId(entity.getId());
				service.atualizar(vendedor);
				return ResponseEntity.ok(vendedor);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () -> new ResponseEntity("Vendedor não encontrado para o Id informador.", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.getById(id).map( entity -> {
			service.deletar(entity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet( () -> new ResponseEntity("Vendedor não encontrado para o Id informado", HttpStatus.BAD_REQUEST));
	}
	
	@GetMapping
	public ResponseEntity buscar(@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "matricula", required = false) String matricula) {
		Vendedor vendedorFiltro = new Vendedor();
		vendedorFiltro.setMatricula(matricula);
		vendedorFiltro.setNome(nome);
		
		List<Vendedor> vendedores = service.buscar(vendedorFiltro);
		return ResponseEntity.ok(vendedores);
	}
	
	private Vendedor converter(VendedorDTO dto) {
		Vendedor vendedor = new Vendedor();
		vendedor.setNome(dto.getNome());
		vendedor.setMatricula(dto.getMatricula());
		return vendedor;
	}

}
