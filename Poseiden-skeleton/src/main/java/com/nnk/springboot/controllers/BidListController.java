package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;

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
public class BidListController {
	
	private static final Logger logger = LoggerFactory.getLogger(BidListController.class);

	@Autowired
	private BidListService bService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
    	logger.info("Showing all bids");
    	model.addAttribute("bids", bService.getBidLists());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public ModelAndView addBidForm(BidList bid) {
    	logger.info("Showing add form");
        return new ModelAndView("bidList/add");
    }
    
    @PostMapping("/bidList/validate")
    public ModelAndView validate(@Valid BidList bid, BindingResult result) {
    	if (!result.hasErrors()) {
    		ModelAndView modelAndView =  new ModelAndView("redirect:/bidList/list");
    		modelAndView.addObject("bids", bService.getBidLists());
    		bService.saveBidList(bid);
    		logger.info("Added bid : "+bid);
            return modelAndView;
        }
    	ModelAndView modelAndView = new ModelAndView("bidList/add");
    	modelAndView.addObject("account", result.getFieldError("account"));
    	modelAndView.addObject("type", result.getFieldError("type"));
    	modelAndView.addObject("bidQuantity", result.getFieldError("bidQuantity"));
    	logger.error(result.getErrorCount()+" errors : "+result.getAllErrors());
    	return modelAndView;
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bid = bService.getBidListById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public ModelAndView updateBid(@PathVariable("id") Integer id,BidList bidList,
                             BindingResult result) {
        if (result.hasErrors()) {
        	ModelAndView modelAndView = new ModelAndView("bidList/update");
        	modelAndView.addObject("account", result.getFieldError("account"));
        	modelAndView.addObject("type", result.getFieldError("type"));
        	modelAndView.addObject("bidQuantity", result.getFieldError("bidQuantity"));
        	logger.error(result.getErrorCount()+" errors : "+result.getAllErrors());
        	return modelAndView;
        }
        else {
        	ModelAndView modelAndView = new ModelAndView("redirect:/bidList/list");
        	bidList.setBidListId(id);
        	bService.saveBidList(bidList);
        	modelAndView.addObject("bids", bService.getBidLists());
        	logger.info("Updated bid with id : "+id);
        	return modelAndView;
        }
    }

    @GetMapping("/bidList/delete/{id}")
    public ModelAndView deleteBid(@PathVariable("id") Integer id) {
    	ModelAndView modelAndView = new ModelAndView("redirect:/bidList/list");
    	bService.getBidListById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
    	bService.deleteBidListById(id);
    	modelAndView.addObject("bids", bService.getBidLists());
    	logger.info("Deleted bid with id : "+id);
        return modelAndView;
    }
}
