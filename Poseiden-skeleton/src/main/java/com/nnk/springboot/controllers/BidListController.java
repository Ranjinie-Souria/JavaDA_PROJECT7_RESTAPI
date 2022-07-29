package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class BidListController {
	
	@Autowired
	private BidListService bService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
    	model.addAttribute("bids", bService.getBidLists());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(BidList bid, BindingResult result, Model model) {
    	if (!result.hasErrors()) {
    		bService.saveBidList(bid);
            model.addAttribute("bids", bService.getBidLists());
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidList bid = bService.getBidListById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id,BidList bidList,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/update";
        }
        bidList.setBidListId(id);
        bService.saveBidList(bidList);
        model.addAttribute("bids", bService.getBidLists());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
    	bService.getBidListById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id:" + id));
    	bService.deleteBidListById(id);
    	model.addAttribute("bids", bService.getBidLists());
        return "redirect:/bidList/list";
    }
}
