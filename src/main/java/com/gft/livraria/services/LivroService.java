package com.gft.livraria.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.livraria.entities.Livro;
import com.gft.livraria.repositories.LivroRepository;

@Service
public class LivroService {
	
	@Autowired
	LivroRepository livroRepository;
	
	public Livro salvarLivro(Livro livro) {
		return livroRepository.save(livro);
	}
	
	public List<Livro> listaLivros(String tema) {
		
		if (tema != null) {
			return listarLivrosPorTema(tema);
		}
		
		return listarTodosLivros();
	}
	
	public List<Livro> listarTodosLivros(){
		return livroRepository.findAll();
	}
	
	private List<Livro> listarLivrosPorTema(String tema){
		return livroRepository.findByTemaContainsIgnoreCase(tema);
	}
	
	public Livro consultarLivro(Long id) throws Exception{
		
		Optional<Livro> livro = livroRepository.findById(id);
		
		if (livro.isEmpty()) {
			throw new Exception("Livro não encontrado!");
		}
		return livro.get();
	}
	
	public void excluirLivro(Long id) {
		livroRepository.deleteById(id);
	}

}
