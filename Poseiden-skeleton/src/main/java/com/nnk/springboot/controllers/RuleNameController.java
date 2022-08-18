package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;

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
public class RuleNameController {
	
	private static final Logger logger = LoggerFactory.getLogger(RuleNameController.class);
	
	@Autowired
	RuleNameService rSer;

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
    	model.addAttribute("ruleNames", rSer.getRuleNames());
    	logger.info("Showing all trades");
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
    	logger.info("Showing add form");
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate( @Valid RuleName ruleName, BindingResult result, Model model) {
    	if (!result.hasErrors()) {
    		rSer.saveRuleName(ruleName);
    		model.addAttribute("ruleNames", rSer.getRuleNames());
    		logger.info("Added ruleName");
            return "redirect:/ruleName/list";
        }
    	logger.error("Errors "+result.getAllErrors());
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	RuleName ruleName = rSer.getRuleNameById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    	model.addAttribute("ruleName", ruleName);
    	logger.info("Showing update form");
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id,@Valid RuleName ruleName,
                             BindingResult result, Model model) {
    	if (result.hasErrors()) {
    		logger.error("Errors "+result.getAllErrors());
            return "ruleName/update";
        }
    	ruleName.setId(id);
    	rSer.saveRuleName(ruleName);
    	model.addAttribute("ruleNames", rSer.getRuleNames());
    	logger.info("updated");
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
    	rSer.getRuleNameById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trades Id:" + id));
    	rSer.deleteRuleNameById(id);
        model.addAttribute("ruleNames", rSer.getRuleNames());
        logger.info("Showing delete form");
        return "redirect:/ruleName/list";
    }
}
