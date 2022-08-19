package com.nnk.springboot.controllers;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("local")
@AutoConfigureMockMvc
@SpringBootTest
class RuleNameControllerTest {
	

	    @Mock
	    RuleNameService service;
	    @InjectMocks
	    RuleNameController controller;
	    
	    @Test
	    void addTest() throws Exception {

	    	RuleName ruleName = new RuleName("name", "description","json","template","sqlStr", "sqlPart");
	    	BindingResult result = mock(BindingResult.class);

	        controller.validate(ruleName, result);

	        verify(service).saveRuleName(ruleName);
	    }


	    @Test
	    void addTestNull() {
	    	RuleName ruleName = null;
	    	BindingResult result = mock(BindingResult.class);
	    	ModelAndView modelAndView = new ModelAndView("RuleName/add");
	    	
	        assertThat(controller.validate(ruleName, result) == modelAndView);
	        
	    }
	    
	    @Test
	    void updateTest() throws Exception {
	    	
	    	RuleName ruleName = new RuleName("name", "description","json","template","sqlStr", "sqlPart");
	    	ruleName.setId(1);
	    	BindingResult result = mock(BindingResult.class);
	    	RuleName edited = new RuleName("edited", "description","json","template","sqlStr", "sqlPart");
	    	
	        controller.updateRuleName(1, edited, result);
	        verify(service).saveRuleName(edited);
	    }


	    @Test
	    void updateNull() {
	    	RuleName ruleName = new RuleName("name", "description","json","template","sqlStr", "sqlPart");
	    	BindingResult result = mock(BindingResult.class);
	    	ModelAndView modelAndView = new ModelAndView("curvePoint/update");
	    	
	        assertThat(controller.updateRuleName(-50,ruleName, result) == modelAndView);
	        
	    }
	    
	    @Test
	    void deleteTest() throws Exception {
	    	
	    	RuleName ruleName = new RuleName("name", "description","json","template","sqlStr", "sqlPart");
	    	ruleName.setId(1);
	    	
	        when(service.getRuleNameById(1)).thenReturn(Optional.of(ruleName));

	        controller.deleteRuleName(1);
	        
	        verify(service).deleteRuleNameById(1);
	    }
	    
	    @Test
	    void deleteNull() {	    	
	        assertThrows(IllegalArgumentException.class, () -> controller.deleteRuleName(-50));
	        
	    }


	
	
}
