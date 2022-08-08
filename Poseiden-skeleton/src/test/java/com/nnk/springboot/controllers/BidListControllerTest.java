package com.nnk.springboot.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.MyUserDetails;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;


@SpringBootTest
@AutoConfigureMockMvc
class BidListControllerTest {
	
	@InjectMocks
	private BidListService bService;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Mock
	private BidListRepository bRepo;

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
	
	@Test
    void addBid() throws Exception {
		mockMvc.perform(post("/bidList/validate")).andExpect(redirectedUrl("http://localhost/login"));
		
		Assertions.assertThrows(NestedServletException.class, () -> 		mockMvc.perform(post("/bidList/validate")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(user(userDetails)))
				.andExpect(status().isFound()));		
		mockMvc.perform(post("/bidList/validate")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(user(userDetails))
				.param("type","type")
				.param("account","account")
				.param("bidQuantity","1"))
				.andExpect(redirectedUrl("/bidList/list"));
//		BidList bid = new BidList("type","account",1);
//		System.out.println(bService.getBidLists().size());
//		Assertions.assertTrue(bService.getBidListByAccount("account").contains(bid));
	}
	
	@Test
    void updateBid() throws Exception {
		BidList bidL = new BidList("type","account",1);
		Optional<BidList> bid = Optional.of(bidL);
		when(bRepo.findById(0)).thenReturn(bid);
		mockMvc.perform(get("/bidList/update/0")).andExpect(redirectedUrl("http://localhost/login"));
		
		mockMvc.perform(post("/bidList/update/0")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(user(userDetails))
				.param("type","type")
				.param("account","account")
				.param("bidQuantity","5"))
				.andExpect(redirectedUrl("/bidList/list"));
		//Assertions.assertEquals(5, bService.getBidListById(0).get().getBidQuantity());
	}
	
	@Test
	void delete() throws Exception {
		BidList bid = new BidList("type","account",1);
		bid.setBidListId(0);
//		bService.saveBidList(bid);
//		Assertions.assertEquals(Optional.of(bid), bService.getBidListById(0));
//		mockMvc.perform(get("/bidList/delete/0")
//				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).with(user(userDetails)))
//				.andExpect(redirectedUrl("/bidList/list"));
		Assertions.assertEquals(Optional.empty(),bService.getBidListById(0));
		
	}
	
	
	
	
	
}
