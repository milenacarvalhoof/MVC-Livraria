package com.gft.livraria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PrincipalController {
	
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index.html");
		
		return mv;
	}
}
