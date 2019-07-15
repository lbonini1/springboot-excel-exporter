package br.com.springboot.api.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.api.model.Pessoa;
import br.com.springboot.api.service.PessoaService;
import br.com.springboot.api.service.relatorios.PessoaExportService;

@RestController
@RequestMapping(value="/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService service;
	
	@Autowired
	private PessoaExportService export;
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping
	public ResponseEntity<List<?>> listarTodasAsPessoas(){
		List<Pessoa> pessoas = service.listarPessoasExistentes();
		if(pessoas.size() > 0) {
			return ResponseEntity.ok().body(pessoas);
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> obterPessoaPorId(@PathVariable Long id){
		if(id != null) {
			return ResponseEntity.ok().body(service.buscarPessoaPorId(id));
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<Pessoa> cadastrarNovaPessoa(@RequestBody Pessoa pessoa){
		if(pessoa != null) {
			return ResponseEntity.ok().body(service.inserirPessoa(pessoa));
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping
	public ResponseEntity<Pessoa> alterarPessoaCadastrada(@RequestBody Pessoa pessoa){
		if(pessoa != null) {
			return ResponseEntity.ok().body(service.alterarPessoa(pessoa));
		}
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> excluirPessoaCadastrada(@PathVariable Long id) {
		if(id != null) {
			Pessoa pessoa = service.buscarPessoaPorId(id);
			if(pessoa.getId() != null)
				service.excluirPessoa(pessoa);
			return ResponseEntity.ok("Registro Excluído com Sucesso.");
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping
	@RequestMapping(value="/exportar")
	public ResponseEntity<?> exportarTodasPessoasExcel(HttpServletResponse response) {
		try{
			export.exportarRelatorioExcel(response);
			return ResponseEntity.ok().build();
		}catch(RuntimeException e) {
			log.error("Não Foi possível gerar o relatório solicitado");
			log.error(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}
}
