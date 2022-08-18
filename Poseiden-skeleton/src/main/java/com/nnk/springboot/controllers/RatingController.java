package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RatingController {
	
	private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

	@Autowired
	RatingService rSer;

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
    	model.addAttribute("ratings", rSer.getRatings());
    	logger.info("Showing all ratings");
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
    	logger.info("Showing add form");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
    	if (!result.hasErrors()) {
    		rSer.saveRating(rating);
            model.addAttribute("ratings", rSer.getRatings());
            logger.info("Added rating : "+rating);
            return "redirect:/rating/list";
        }
    	logger.error("error : "+result.getAllErrors());
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	Rating rating = rSer.getRatingById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
    	model.addAttribute("rating", rating);
    	logger.info("Showing update form");
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id,@Valid Rating rating,
                             BindingResult result, Model model) {
    	if (result.hasErrors()) {
    		logger.error("error : "+result.getAllErrors());
            return "rating/update";
        }
    	rating.setId(id);
    	rSer.saveRating(rating);
        model.addAttribute("ratings", rSer.getRatings());
        logger.info("Updated rating");
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
    	rSer.getRatingById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
    	rSer.deleteRatingById(id);
        model.addAttribute("ratings", rSer.getRatings());
        logger.info("Showing delete form");
        return "redirect:/rating/list";
    }
}
