package com.nnk.springboot.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.nnk.springboot.domain.MyUserDetails;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.BidListService;


@SpringBootTest
@AutoConfigureMockMvc
class BidListControllerTest {
	
	@InjectMocks
	private BidListService bService;
	
	@Autowired
    private MockMvc mockMvc;

	private MyUserDetails adminDetails = new MyUserDetails(new User(0,"admin","admin","admin","ADMIN"));
	private MyUserDetails userDetails = new MyUserDetails(new User(1,"user","user","user","USER"));
	
	@Test
	void showHome() throws Exception {
		mockMvc.perform(get("/user/list").with(user(adminDetails))).andExpect(status().isOk());
		mockMvc.perform(get("/bidList/list").with(user(userDetails))).andExpect(status().isOk());
		mockMvc.perform(get("/user/list").with(user(userDetails))).andExpect(status().isForbidden());
	}
	
	@Test
    void getBidForm() throws Exception {
		mockMvc.perform(get("/bidList/add")).andExpect(redirectedUrl("http://localhost/login"));
		mockMvc.perform(get("/bidList/add").with(user(adminDetails))).andExpect(status().isOk());
		mockMvc.perform(get("/bidList/add").with(user(userDetails))).andExpect(status().isOk());
    }
	
	
	
	
	
}
