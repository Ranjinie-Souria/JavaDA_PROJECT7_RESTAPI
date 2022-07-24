package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

public class TradeService {

	@Autowired
	private TradeRepository tradeRepository;
	
	public List<Trade> getTrades() {
		return tradeRepository.findAll();
	}
	
	public Optional<Trade> getTradeById(Integer id) {
		return tradeRepository.findById(id);
	}
	
	public Trade saveTrade(Trade trade) {
		return tradeRepository.save(trade);		
	}
	
	public void deleteTradeById(Integer id) {
		tradeRepository.deleteById(id);
	}

}
