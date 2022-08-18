package com.nnk.springboot.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultController.class);
	
    @RequestMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    	boolean hasAdminRole = authentication.getAuthorities().stream()
    	          .anyMatch(r -> r.getAuthority().equals("ADMIN"));
        if (hasAdminRole) {
        	logger.info("Admin logged");
            return "redirect:/user/list/";
        }
        logger.info("User logged");
        return "redirect:/bidList/list";
    }
}
