package com.gft.livraria.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gft.livraria.entities.Autor;
import com.gft.livraria.repositories.AutorRepository;

@Service
public class AutorService {
	
	@Autowired
	AutorRepository autorRepository;
	
	public Autor salvarAutor(Autor autor) {
		return autorRepository.save(autor);
	}
	
	public List<Autor> listaAutores() {
		return autorRepository.findAll();
	}
	
	public Autor consultarAutor(Long id) throws Exception{
		
		Optional<Autor> autor = autorRepository.findById(id);
		
		if (autor.isEmpty()) {
			throw new Exception("Autor não encontrado!");
		}
		return autor.get();
	}
	
	public void excluirAutor(Long id) {
		autorRepository.deleteById(id);
	}

}
