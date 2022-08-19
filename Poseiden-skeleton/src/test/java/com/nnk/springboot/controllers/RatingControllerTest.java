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
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("local")
@AutoConfigureMockMvc
@SpringBootTest
class RatingControllerTest {
	

	    @Mock
	    RatingService service;
	    @InjectMocks
	    RatingController controller;
	    
	    @Test
	    void addTest() throws Exception {

	    	Rating rating = new Rating("moodysRating","sandPRating","fitchRating",1);
	    	BindingResult result = mock(BindingResult.class);

	        controller.validate(rating, result);

	        verify(service).saveRating(rating);
	    }


	    @Test
	    void addTestNull() {
	    	Rating rating = null;
	    	BindingResult result = mock(BindingResult.class);
	    	ModelAndView modelAndView = new ModelAndView("bidList/add");
	    	
	        assertThat(controller.validate(rating, result)== modelAndView);
	        
	    }
	    
	    @Test
	    void updateTest() throws Exception {
	    	
	    	Rating rating = new Rating("moodysRating","sandPRating","fitchRating",1);
	    	rating.setId(1);
	    	BindingResult result = mock(BindingResult.class);
	    	Rating edited = new Rating("edited","sandPRating","fitchRating",1);
	    	
	        controller.updateRating(1, edited, result);
	        verify(service).saveRating(edited);
	    }


	    @Test
	    void updateNull() {
	    	Rating rating = new Rating("moodysRating","sandPRating","fitchRating",1);
	    	BindingResult result = mock(BindingResult.class);
	    	ModelAndView modelAndView = new ModelAndView("rating/update");
	    	
	        assertThat(controller.updateRating(-50,rating, result)== modelAndView);
	        
	    }
	    
	    @Test
	    void deleteTest() throws Exception {
	    	
	    	Rating rating = new Rating("moodysRating","sandPRating","fitchRating",1);
	    	rating.setId(1);
	    	
	        when(service.getRatingById(1)).thenReturn(Optional.of(rating));

	        controller.deleteRating(1);
	        
	        verify(service).deleteRatingById(1);
	    }
	    
	    @Test
	    void deleteNull() {	    	
	        assertThrows(IllegalArgumentException.class, () -> controller.deleteRating(-50));
	        
	    }


	
	
}
