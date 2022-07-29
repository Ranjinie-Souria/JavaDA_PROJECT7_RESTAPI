package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CurveController {
	
	@Autowired
	private CurvePointService cSer;

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
    	model.addAttribute("curves", cSer.getCurvePoints());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(CurvePoint curvePoint, BindingResult result, Model model) {
    	if (!result.hasErrors()) {
    		cSer.saveCurvePoint(curvePoint);
            model.addAttribute("curves", cSer.getCurvePoints());
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	CurvePoint curvePoint = cSer.getCurvePointById(id).orElseThrow(() -> new IllegalArgumentException("Invalid curvePoint Id:" + id));
    	model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id,CurvePoint curvePoint,
                             BindingResult result, Model model) {
    	if (result.hasErrors()) {
            return "curvePoint/update";
        }
    	curvePoint.setId(id);
    	cSer.saveCurvePoint(curvePoint);
        model.addAttribute("curves", cSer.getCurvePoints());
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, Model model) {
    	cSer.getCurvePointById(id).orElseThrow(() -> new IllegalArgumentException("Invalid CurvePoint Id:" + id));
    	cSer.deleteCurvePointById(id);
    	model.addAttribute("curves", cSer.getCurvePoints());
        return "redirect:/curvePoint/list";
    }
}
