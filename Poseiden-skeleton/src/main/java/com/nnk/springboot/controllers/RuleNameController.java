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
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView validate( @Valid RuleName ruleName, BindingResult result) {
    	if (!result.hasErrors()) {
    		ModelAndView model =  new ModelAndView("redirect:/ruleName/list");
    		rSer.saveRuleName(ruleName);
    		model.addObject("ruleNames", rSer.getRuleNames());
    		logger.info("Added ruleName");
            return model;
        }
    	ModelAndView model =  new ModelAndView("ruleName/add");
    	logger.error("Errors "+result.getAllErrors());
        return model;
    }

    @GetMapping("/ruleName/update/{id}")
    public ModelAndView showUpdateForm(@PathVariable("id") Integer id) {
    	ModelAndView model =  new ModelAndView("ruleName/update");
    	RuleName ruleName = rSer.getRuleNameById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id:" + id));
    	model.addObject("ruleName", ruleName);
    	logger.info("Showing update form");
        return model;
    }

    @PostMapping("/ruleName/update/{id}")
    public ModelAndView updateRuleName(@PathVariable("id") Integer id,@Valid RuleName ruleName,
                             BindingResult result) {
    	if (result.hasErrors()) {
    		ModelAndView model =  new ModelAndView("redirect:/ruleName/update");
    		logger.error("Errors "+result.getAllErrors());
            return model;
        }
    	ModelAndView model =  new ModelAndView("redirect:/ruleName/list");
    	ruleName.setId(id);
    	rSer.saveRuleName(ruleName);
    	model.addObject("ruleNames", rSer.getRuleNames());
    	logger.info("updated");
        return model;
    }

    @GetMapping("/ruleName/delete/{id}")
    public ModelAndView deleteRuleName(@PathVariable("id") Integer id) {
    	ModelAndView model =  new ModelAndView("redirect:/ruleName/list");
    	rSer.getRuleNameById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trades Id:" + id));
    	rSer.deleteRuleNameById(id);
        model.addObject("ruleNames", rSer.getRuleNames());
        logger.info("Showing delete form");
        return model;
    }
}
