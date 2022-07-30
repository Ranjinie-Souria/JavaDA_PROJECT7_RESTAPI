package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TradeController {

	@Autowired
	TradeService tSer;

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
    	model.addAttribute("trades", tSer.getTrades());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTrade(Trade trade) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(Trade trade, BindingResult result, Model model) {
    	if (!result.hasErrors()) {
    		tSer.saveTrade(trade);
    		model.addAttribute("trades", tSer.getTrades());
            return "redirect:/trade/list";
        }
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	Trade trade = tSer.getTradeById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    	model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, Trade trade,
                             BindingResult result, Model model) {
    	if (result.hasErrors()) {
            return "trade/update";
        }
    	trade.setTradeId(id);
    	tSer.saveTrade(trade);
    	model.addAttribute("trades", tSer.getTrades());
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
    	tSer.getTradeById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trades Id:" + id));
    	tSer.deleteTradeById(id);
        model.addAttribute("trades", tSer.getTrades());
        return "redirect:/trade/list";
    }
}
