package com.nnk.springboot;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BidTests {
//
//	@Autowired
//	private BidListController bidC;
//	
//	@Mock
//	private BidListService bidS;
//	
//	@Autowired
//    private MockMvc mockMvc;
//
//	@Test
//	public void saveBidTest() {
//		BidList bid = new BidList("Account Test", "Type Test", 10d);
//		// Save
//		bid = bidListRepository.save(bid);
//		Assert.assertNotNull(bid.getBidListId());
//		Assert.assertEquals(bid.getBidQuantity(), 10d, 10d);
//	}
//	
//	@Test
//	public void updateBidTest() {
//		BidList bid = new BidList("Account Test", "Type Test", 10d);
//		// Update
//		bid.setBidQuantity(20d);
//		bid = bidListRepository.save(bid);
//		Assert.assertEquals(bid.getBidQuantity(), 20d, 20d);
//	}
//	
//	@Test
//	public void getBidTest() {
//		// Find
//		//mockMvc.perform(get("/connection").with(user(userDetails))).andExpect(status().isOk());
//		List<BidList> listResult = bidListRepository.findAll();
//		Assert.assertTrue(listResult.size() > 0);
//	}
//	
//	@Test
//	public void deleteBidTest() {
//		BidList bid = new BidList("Account Test", "Type Test", 10d);
//		bid = bidListRepository.save(bid);
//		// Delete
//		Integer id = bid.getBidListId();
//		bidListRepository.delete(bid);
//		Optional<BidList> bidList = bidListRepository.findById(id);
//		Assert.assertFalse(bidList.isPresent());
//	}
}
