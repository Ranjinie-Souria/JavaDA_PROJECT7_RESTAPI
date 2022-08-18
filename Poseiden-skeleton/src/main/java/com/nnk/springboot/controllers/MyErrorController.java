package com.nnk.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyErrorController implements ErrorController{

	private static final Logger logger = LoggerFactory.getLogger(MyErrorController.class);
	
    @GetMapping("/error")
    public String error(ModelMap model) {
    	logger.error("Error 403");
        String errorMessage= "You are not authorized for the requested data.";
        model.addAttribute("errorMsg", errorMessage);
        return "403";
    }

}
