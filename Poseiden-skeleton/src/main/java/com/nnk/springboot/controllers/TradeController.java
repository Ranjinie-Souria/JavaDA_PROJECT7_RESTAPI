package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;

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
public class TradeController {

	private static final Logger logger = LoggerFactory.getLogger(TradeController.class);
	
	@Autowired
	TradeService tSer;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
    	model.addAttribute("trades", tSer.getTrades());
    	logger.info("Showing all trades");
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTrade(Trade trade) {
    	logger.info("Showing add form");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public ModelAndView validate(@Valid Trade trade, BindingResult result) {
    	if (!result.hasErrors()) {
    		ModelAndView model =  new ModelAndView("redirect:/trade/list");
    		tSer.saveTrade(trade);
    		model.addObject("trades", tSer.getTrades());
    		logger.info("Added trade");
            return model;
        }
    	ModelAndView model =  new ModelAndView("trade/add");
    	logger.error("Errors "+result.getAllErrors());
        return model;
    }

    @GetMapping("/trade/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") Integer id) {
    	ModelAndView model =  new ModelAndView("trade/update");
    	Trade trade = tSer.getTradeById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    	model.addObject("trade", trade);
    	logger.info("Showing update form");
        return model;
    }

    @PostMapping("/trade/update/{id}")
    public ModelAndView updateTrade(@PathVariable("id") Integer id,@Valid  Trade trade,
                             BindingResult result) {
    	if (result.hasErrors()) {
    		ModelAndView model =  new ModelAndView("trade/update");
    		logger.error("Errors "+result.getAllErrors());
            return model;
        }
    	ModelAndView model =  new ModelAndView("redirect:/trade/list");
    	trade.setTradeId(id);
    	tSer.saveTrade(trade);
    	model.addObject("trades", tSer.getTrades());
    	logger.info("Updated trade");
        return model;
    }

    @GetMapping("/trade/delete/{id}")
    public ModelAndView deleteTrade(@PathVariable("id") Integer id) {
    	ModelAndView model =  new ModelAndView("redirect:/trade/list");
    	tSer.getTradeById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trades Id:" + id));
    	tSer.deleteTradeById(id);
        model.addObject("trades", tSer.getTrades());
        logger.info("Deleted trade");
        return model;
    }
}
