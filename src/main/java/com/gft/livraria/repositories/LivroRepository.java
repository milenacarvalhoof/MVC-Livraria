package com.gft.livraria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gft.livraria.entities.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
	
	List<Livro> findByTemaContainsIgnoreCase(String tema);

}
