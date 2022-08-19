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
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("local")
@AutoConfigureMockMvc
@SpringBootTest
class TradeControllerTest {
	

	    @Mock
	    TradeService service;
	    @InjectMocks
	    TradeController controller;
	    
	    @Test
	    void addTest() throws Exception {

	    	Trade trade = new Trade("account","type",1.0);
	    	BindingResult result = mock(BindingResult.class);

	        controller.validate(trade, result);

	        verify(service).saveTrade(trade);
	    }


	    @Test
	    void addTestNull() {
	    	Trade trade = null;
	    	BindingResult result = mock(BindingResult.class);
	    	ModelAndView modelAndView = new ModelAndView("bidList/add");
	    	
	        assertThat(controller.validate(trade, result)== modelAndView);
	        
	    }
	    
	    @Test
	    void updateTest() throws Exception {
	    	
	    	Trade trade = new Trade("account","type",1.0);
	    	trade.setTradeId(1);
	    	BindingResult result = mock(BindingResult.class);
	    	Trade edited = new Trade("edited","type",1.0);
	    	
	        controller.updateTrade(1, edited, result);
	        verify(service).saveTrade(edited);
	    }


	    @Test
	    void updateNull() {
	    	Trade trade = new Trade("account","type",1.0);
	    	BindingResult result = mock(BindingResult.class);
	    	ModelAndView modelAndView = new ModelAndView("rating/update");
	    	
	        assertThat(controller.updateTrade(-50,trade, result)== modelAndView);
	        
	    }
	    
	    @Test
	    void deleteTest() throws Exception {
	    	
	    	Trade trade = new Trade("account","type",1.0);
	    	trade.setTradeId(1);
	    	
	        when(service.getTradeById(1)).thenReturn(Optional.of(trade));

	        controller.deleteTrade(1);
	        
	        verify(service).deleteTradeById(1);
	    }
	    
	    @Test
	    void deleteNull() {	    	
	        assertThrows(IllegalArgumentException.class, () -> controller.deleteTrade(-50));
	        
	    }


	
	
}
