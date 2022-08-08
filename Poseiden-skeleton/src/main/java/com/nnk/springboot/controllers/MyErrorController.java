package com.nnk.springboot.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyErrorController implements ErrorController{


    @GetMapping("/error")
    public String error(ModelMap model) {
        String errorMessage= "You are not authorized for the requested data.";
        model.addAttribute("errorMsg", errorMessage);
        return "403";
    }

}
