package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

@Service
public class BidListService {
	
	@Autowired
	private BidListRepository bidListRepository;
	
	public List<BidList> getBidLists() {
		return bidListRepository.findAll();
	}
	
	public Optional<BidList> getBidListById(Integer id) {
		return bidListRepository.findById(id);
	}
	
	public BidList saveBidList(BidList bidList) {
		return bidListRepository.save(bidList);		
	}
	
	public void deleteBidListById(Integer id) {
		bidListRepository.deleteById(id);
	}
	
	public List<BidList> getBidListByAccount(String account) {
		return bidListRepository.findByAccount(account);
	}
	
	


}
