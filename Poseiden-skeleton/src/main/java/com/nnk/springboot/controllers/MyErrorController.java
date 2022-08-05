package com.nnk.springboot.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class MyErrorController implements ErrorController{


	@GetMapping("/error")
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	public String serviceForbidden() {
	return "You are not allowed to access this page\n";
	}

}
