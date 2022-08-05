package com.nnk.springboot.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
	
    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    	boolean hasAdminRole = authentication.getAuthorities().stream()
    	          .anyMatch(r -> r.getAuthority().equals("ADMIN"));
        if (hasAdminRole) {
            return "redirect:/user/list/";
        }
        return "redirect:/bidList/list";
    }
}
