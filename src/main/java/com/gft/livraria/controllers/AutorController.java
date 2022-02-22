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

import com.gft.livraria.entities.Autor;
import com.gft.livraria.services.AutorService;

@Controller
@RequestMapping(path = "autor")
public class AutorController {
	
	@Autowired
	private AutorService autorService;

	@RequestMapping(path = "novo") // http://localhost:8080/autor/novo
	public ModelAndView novoAutor() {
		
		ModelAndView mv = new ModelAndView("autor/form.html");
		mv.addObject("autor", new Autor());
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "novo") // http://localhost:8080/autor/novo
	public ModelAndView salvarAutor(@Valid Autor autor, BindingResult bindingResult) {
		
		ModelAndView mv = new ModelAndView("autor/form.html");
		
		boolean novo = true;
		
		if (autor.getId() != null) {
			novo = false;
		}
		
		if (bindingResult.hasErrors()) {
			mv.addObject("autor", autor);
			return mv;
		}
		
		autorService.salvarAutor(autor);
		
		if (novo) {
			mv.addObject("autor", new Autor());
		} else {
			mv.addObject("autor", autor);
		}
		
		mv.addObject("mensagem", "Autor salvo com sucesso!");
		
		return mv;
	}
	
	@RequestMapping // http://localhost:8080/autor
	public ModelAndView listarAutores() {
		
		ModelAndView mv = new ModelAndView("autor/listar.html");
		mv.addObject("lista", autorService.listaAutores());
		
		return mv;
	}
	
	@RequestMapping(path = "/editar") // http://localhost:8080/autor/editar
	public ModelAndView editarLinguagem(@RequestParam Long id) {
		
		ModelAndView mv = new ModelAndView("autor/form.html");
		Autor autor;
		
		try {
			autor = autorService.consultarAutor(id);
		} catch (Exception e) {
			autor = new Autor();
			mv.addObject("mensagem", e.getMessage());
		}
		
		mv.addObject("autor", autor);
		return mv;
	}
	
	@RequestMapping(path = "/excluir") // http://localhost:8080/autor/excluir
	public ModelAndView excluirAutor(@RequestParam Long id, RedirectAttributes redirectAttributes) {
		
		ModelAndView mv = new ModelAndView("redirect:/autor");
		
		try {
			autorService.excluirAutor(id);
			redirectAttributes.addFlashAttribute("mensagem", "Autor Excluído com sucesso!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("mensagem", "Erro ao excluir autor!" + e.getMessage());
		}
		
		return mv;
	}

}
