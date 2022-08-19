package com.nnk.springboot.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.MyUserDetails;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.BidListService;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class BidListControllerTestIT {
	
	@Autowired
	private BidListService bService;
	
	@Autowired
    private MockMvc mockMvc;


	private MyUserDetails adminDetails = new MyUserDetails(new User(0,"admin","admin","admin","ADMIN"));
	private MyUserDetails userDetails = new MyUserDetails(new User(1,"user","user","user","USER"));
	
	@AfterEach
    public void cleanUpEach(){
        System.out.println("After Each cleanUpEach() method called");
    }
	
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
	
	@Test
    void addBid() throws Exception {
		mockMvc.perform(post("/bidList/validate")).andExpect(redirectedUrl("http://localhost/login"));
		int size = bService.getBidLists().size();
		mockMvc.perform(post("/bidList/validate")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(user(userDetails))
				.param("type","type")
				.param("account","account")
				.param("bidQuantity","1"))
				.andExpect(redirectedUrl("/bidList/list"));
		Assertions.assertTrue(bService.getBidLists().size()==size+1);
	}
	
	@Test
    void updateBid() throws Exception {
		BidList bidL = new BidList("type","account",4);
		bidL.setBidListId(4);		
		mockMvc.perform(post("/bidList/update/4")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(user(userDetails))
				.param("type","type")
				.param("account","account")
				.param("bidQuantity","5"))
				.andExpect(redirectedUrl("/bidList/list"));
		Assertions.assertEquals(5, bService.getBidListById(1).get().getBidQuantity());
	}
	
	@Test
	void delete() throws Exception {
		int id = -5;
		if(bService.getBidListByAccount("bidToDelete").isEmpty()) {
			mockMvc.perform(post("/bidList/validate")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(user(userDetails))
					.param("type","type")
					.param("account","bidToDelete")
					.param("bidQuantity","1"));
			BidList bid = bService.getBidListByAccount("bidToDelete").get(0);
			id = bid.getBidListId();
		}
		int size = bService.getBidLists().size();
		mockMvc.perform(get("/bidList/delete/"+id)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(user(userDetails)))
				.andExpect(redirectedUrl("/bidList/list"));
		Assertions.assertEquals(size-1,bService.getBidLists().size());
		
	}
	
	
	
	
	
	
	
}
