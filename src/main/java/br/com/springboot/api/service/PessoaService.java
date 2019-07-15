package br.com.springboot.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.springboot.api.model.Pessoa;
import br.com.springboot.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository repository;
	
	public List<Pessoa> listarPessoasExistentes(){
		return repository.findAll();
	}
	
	public Pessoa buscarPessoaPorId(Long id) {
		return repository.getOne(id);
	}
	
	public Pessoa inserirPessoa(Pessoa pessoa) {
		return repository.save(pessoa);
	}
	
	public Pessoa alterarPessoa(Pessoa pessoa) {
		return repository.save(pessoa);
	}
	
	public void excluirPessoa(Pessoa pessoa) {
		repository.delete(pessoa);
	}
	
}
