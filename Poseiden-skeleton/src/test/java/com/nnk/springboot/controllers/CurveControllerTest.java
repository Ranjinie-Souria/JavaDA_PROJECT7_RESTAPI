package com.nnk.springboot.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.mockito.junit.jupiter.MockitoExtension;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("local")
@AutoConfigureMockMvc
@SpringBootTest
class CurveControllerTest {
	

	    @Mock
	    CurvePointService service;
	    @InjectMocks
	    CurveController controller;
	    
	    @Test
	    void addTest() throws Exception {

	    	CurvePoint curve = new CurvePoint(0,0,0);
	    	BindingResult result = mock(BindingResult.class);

	        controller.validate(curve, result);

	        verify(service).saveCurvePoint(curve);
	    }


	    @Test
	    void addTestNull() {
	    	CurvePoint curve = null;
	    	BindingResult result = mock(BindingResult.class);
	    	ModelAndView modelAndView = new ModelAndView("curvePoint/add");
	    	
	        assertThat(controller.validate(curve, result) == modelAndView);
	        
	    }
	    
	    @Test
	    void updateTest() throws Exception {
	    	
	    	CurvePoint curve = new CurvePoint(0,0,0);
	    	curve.setCurveId(1);
	    	BindingResult result = mock(BindingResult.class);
	    	CurvePoint edited = new CurvePoint(0,2,0);
	    	
	        controller.updateCurve(1, edited, result);
	        verify(service).saveCurvePoint(edited);
	    }


	    @Test
	    void updateNull() {
	    	CurvePoint curve = new CurvePoint(0,0,0);
	    	BindingResult result = mock(BindingResult.class);
	    	ModelAndView modelAndView = new ModelAndView("curvePoint/update");
	    	
	        assertThat(controller.updateCurve(-50,curve, result) == modelAndView);
	        
	    }
	    
	    @Test
	    void deleteTest() throws Exception {
	    	
	    	CurvePoint curve = new CurvePoint(0,0,0);
	    	curve.setCurveId(1);
	    	
	        when(service.getCurvePointById(1)).thenReturn(Optional.of(curve));

	        controller.deleteCurve(1);
	        
	        verify(service).deleteCurvePointById(1);
	    }
	    
	    @Test
	    void deleteNull() {	    	
	        assertThrows(IllegalArgumentException.class, () -> controller.deleteCurve(-50));
	        
	    }


	
	
}
