package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
    @Autowired
    private UserService userS;

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userS.getUsers());
        logger.info("Showing all users");
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User bid) {
    	logger.info("Showing add form");
        return "user/add";
    }

    @PostMapping("/user/validate")
    public ModelAndView validate(@Valid User user, BindingResult result, Model model) {
    	
        if (!result.hasErrors()) {
        	ModelAndView modelAndView = new ModelAndView("redirect:/user/list");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userS.saveUser(user);
            modelAndView.addObject("users", userS.getUsers());
            logger.info("Added user "+user.getUsername());
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("user/add");
    	modelAndView.addObject("username", result.getFieldError("username"));
    	modelAndView.addObject("fullname", result.getFieldError("fullname"));
    	modelAndView.addObject("password", result.getFieldError("password"));
    	modelAndView.addObject("role", result.getFieldError("role"));
    	logger.error(result.getErrorCount()+" errors : "+result.getAllErrors());
        return modelAndView;
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userS.getUserById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");
        model.addAttribute("user", user);
        logger.info("Showing update form");
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id,@Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
        	logger.error("Error while updating user : "+result.getAllErrors());
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userS.saveUser(user);
        model.addAttribute("users", userS.getUsers());
        logger.info("Updated user");
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userS.getUserById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userS.deleteUserById(id);
        model.addAttribute("users", userS.getUsers());
        logger.info("Deleting user");
        return "redirect:/user/list";
    }
}
