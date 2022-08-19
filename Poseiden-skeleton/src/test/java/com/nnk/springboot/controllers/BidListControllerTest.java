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
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("local")
@AutoConfigureMockMvc
@SpringBootTest
class BidListControllerTest {
	

	    @Mock
	    BidListService service;
	    @InjectMocks
	    BidListController controller;
	    
	    @Test
	    void addTest() throws Exception {

	    	BidList bid = new BidList("type","account",1);
	    	BindingResult result = mock(BindingResult.class);

	        controller.validate(bid, result);

	        verify(service).saveBidList(bid);
	    }


	    @Test
	    void addTestNull() {
	    	BidList bid = null;
	    	BindingResult result = mock(BindingResult.class);
	    	ModelAndView modelAndView = new ModelAndView("bidList/add");
	    	
	        assertThat(controller.validate(bid, result)== modelAndView);
	        
	    }
	    
	    @Test
	    void updateTest() throws Exception {
	    	
	    	BidList bid = new BidList("type","account",1);
	    	bid.setBidListId(1);
	    	BindingResult result = mock(BindingResult.class);
	    	BidList edited = new BidList("type","edited",1);
	    	
	        controller.updateBid(1, edited, result);
	        assertFalse(bid.getAccount() != edited.getAccount());
	        verify(service).saveBidList(edited);
	    }


	    @Test
	    void updateNull() {
	    	BidList bid = new BidList("type","account",1);
	    	BindingResult result = mock(BindingResult.class);
	    	ModelAndView modelAndView = new ModelAndView("bidList/update");
	    	
	        assertThat(controller.updateBid(-50,bid, result)== modelAndView);
	        
	    }
	    
	    @Test
	    void deleteTest() throws Exception {
	    	
	    	BidList bid = new BidList("type","account",1);
	    	bid.setBidListId(1);
	    	
	        when(service.getBidListById(1)).thenReturn(Optional.of(bid));

	        controller.deleteBid(1);
	        
	        verify(service).deleteBidListById(1);
	    }
	    
	    @Test
	    void deleteNull() {	    	
	        assertThrows(IllegalArgumentException.class, () -> controller.deleteBid(-50));
	        
	    }


	
	
}
