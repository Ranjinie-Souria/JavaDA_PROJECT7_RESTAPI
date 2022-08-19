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
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView validate(@Valid Rating rating, BindingResult result) {
    	if (!result.hasErrors()) {
    		ModelAndView model =  new ModelAndView("redirect:/rating/list");
    		rSer.saveRating(rating);
            model.addObject("ratings", rSer.getRatings());
            logger.info("Added rating : "+rating);
            return model;
        }
    	ModelAndView model =  new ModelAndView("rating/add");
    	logger.error("error : "+result.getAllErrors());
        return model;
    }

    @GetMapping("/rating/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") Integer id) {
    	ModelAndView model =  new ModelAndView("rating/update");
    	Rating rating = rSer.getRatingById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
    	model.addObject("rating", rating);
    	logger.info("Showing update form");
        return model;
    }

    @PostMapping("/rating/update/{id}")
    public ModelAndView updateRating(@PathVariable("id") Integer id,@Valid Rating rating,
                             BindingResult result) {
    	if (result.hasErrors()) {
    		ModelAndView model =  new ModelAndView("rating/update");
    		logger.error("error : "+result.getAllErrors());
            return model;
        }
    	ModelAndView model =  new ModelAndView("redirect:/rating/list");
    	rating.setId(id);
    	rSer.saveRating(rating);
    	model.addObject("ratings", rSer.getRatings());
        logger.info("Updated rating");
        return model;
    }

    @GetMapping("/rating/delete/{id}")
    public ModelAndView deleteRating(@PathVariable("id") Integer id) {
    	ModelAndView model =  new ModelAndView("redirect:/rating/list");
    	rSer.getRatingById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id:" + id));
    	rSer.deleteRatingById(id);
    	model.addObject("ratings", rSer.getRatings());
        logger.info("Showing delete form");
        return model;
    }
}
