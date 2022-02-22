package com.gft.livraria.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gft.livraria.entities.Livro;
import com.gft.livraria.services.AutorService;
import com.gft.livraria.services.LivroService;

@Controller
@RequestMapping(path = "livro")
public class LivroController {

	@Autowired
	private LivroService livroService;
	
	@Autowired
	private AutorService autorService;

	@RequestMapping(path = "novo") // http://localhost:8080/livro/novo
	public ModelAndView novoLivro() {
		
		ModelAndView mv = new ModelAndView("livro/form.html");
		mv.addObject("livro", new Livro());
		mv.addObject("listaAutores", autorService.listaAutores());
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "novo") // http://localhost:8080/livro/novo
	public ModelAndView salvarLivro(@Valid Livro livro, BindingResult bindingResult) {
		
		ModelAndView mv = new ModelAndView("livro/form.html");
		
		boolean novo = true;
		
		if (livro.getId() != null) {
			novo = false;
		}
		
		if (bindingResult.hasErrors()) {
			mv.addObject("livro", livro);
			return mv;
		}
		
		livroService.salvarLivro(livro);
		
		if (novo) {
			mv.addObject("livro", new Livro());
		} else {
			mv.addObject("livro", livro);
		}
		
		mv.addObject("mensagem", "Livro salvo com sucesso!");
		mv.addObject("listaAutores", autorService.listaAutores());
		
		return mv;
	}
	
	@RequestMapping // http://localhost:8080/livro
	public ModelAndView listarLivros(String tema) {
		
		ModelAndView mv = new ModelAndView("livro/listar.html");
		mv.addObject("lista", livroService.listaLivros(tema));
		
		mv.addObject("tema", tema);
		
		return mv;
	}
	
	@RequestMapping(path = "/editar") // http://localhost:8080/livro/editar
	public ModelAndView editarLivro(@RequestParam Long id) {
		
		ModelAndView mv = new ModelAndView("livro/form.html");
		Livro livro;
		
		try {
			livro = livroService.consultarLivro(id);
		} catch (Exception e) {
			livro = new Livro();
			mv.addObject("mensagem", e.getMessage());
		}
		
		mv.addObject("livro", livro);
		mv.addObject("listaAutores", autorService.listaAutores());
		
		return mv;
	}
	
	@RequestMapping(path = "/excluir") // http://localhost:8080/livro/excluir
	public ModelAndView excluirLivro(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("redirect:/livro");
		
		try {
			livroService.excluirLivro(id);
			redirectAttributes.addFlashAttribute("mensagem", "Livro Excluído com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir livro!" + e.getMessage());
		}
		
		return mv;
	}
}
