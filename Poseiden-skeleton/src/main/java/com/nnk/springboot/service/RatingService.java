package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

public class RatingService {
	

	@Autowired
	private RatingRepository ratingRepository;
	
	public List<Rating> getRuleNames() {
		return ratingRepository.findAll();
	}
	
	public Optional<Rating> getRuleNameById(Integer id) {
		return ratingRepository.findById(id);
	}
	
	public Rating saveRatingName(Rating trade) {
		return ratingRepository.save(trade);		
	}
	
	public void deleteRatingNameById(Integer id) {
		ratingRepository.deleteById(id);
	}

}
