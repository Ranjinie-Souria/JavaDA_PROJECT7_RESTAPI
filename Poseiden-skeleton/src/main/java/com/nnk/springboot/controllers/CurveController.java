package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;

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
public class CurveController {
	
	private static final Logger logger = LoggerFactory.getLogger(CurveController.class);
	
	@Autowired
	private CurvePointService cSer;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
    	logger.info("Showing all curves");
    	model.addAttribute("curves", cSer.getCurvePoints());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePoint bid) {
    	logger.info("Showing add form");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public ModelAndView validate(@Valid CurvePoint curvePoint, BindingResult result) {
    	if (!result.hasErrors()) {
    		ModelAndView modelAndView =  new ModelAndView("redirect:/curvePoint/list");       
    		cSer.saveCurvePoint(curvePoint);
    		modelAndView.addObject("curves", cSer.getCurvePoints());
            logger.info("Added curve : "+curvePoint);
            return modelAndView;
        }
    	ModelAndView modelAndView = new ModelAndView("curvePoint/add");
    	modelAndView.addObject("account", result.getFieldError("account"));
    	modelAndView.addObject("type", result.getFieldError("type"));
    	modelAndView.addObject("bidQuantity", result.getFieldError("bidQuantity"));
    	logger.error(result.getErrorCount()+" Errors "+result.getAllErrors());
        return modelAndView;
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	CurvePoint curvePoint = cSer.getCurvePointById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    	model.addAttribute("curvePoint", curvePoint);
    	logger.info("Showing update form");
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public ModelAndView updateCurve(@PathVariable("id") Integer id,@Valid CurvePoint curvePoint,
                             BindingResult result) {
    	if (result.hasErrors()) {
    		ModelAndView modelAndView = new ModelAndView("curvePoint/update");
    		logger.error("Errors "+result.getAllErrors());
            return modelAndView;
        }
    	ModelAndView modelAndView = new ModelAndView("redirect:/curvePoint/list");
    	curvePoint.setId(id);
    	cSer.saveCurvePoint(curvePoint);
    	modelAndView.addObject("curves", cSer.getCurvePoints());
        logger.info("updated");
        return modelAndView;
    }

    @GetMapping("/curvePoint/delete/{id}")
    public ModelAndView deleteCurve(@PathVariable("id") Integer id) {
    	ModelAndView modelAndView = new ModelAndView("redirect:/curvePoint/list");
    	cSer.getCurvePointById(id).orElseThrow(() -> new IllegalArgumentException("Invalid CurvePoint Id:" + id));
    	cSer.deleteCurvePointById(id);
    	modelAndView.addObject("curves", cSer.getCurvePoints());
    	logger.info("Showing delete form");
        return modelAndView;
    }
}
