package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

public class RuleNameService {


	@Autowired
	private RuleNameRepository ruleNameRepository;
	
	public List<RuleName> getRuleNames() {
		return ruleNameRepository.findAll();
	}
	
	public Optional<RuleName> getRuleNameById(Integer id) {
		return ruleNameRepository.findById(id);
	}
	
	public RuleName saveRuleName(RuleName trade) {
		return ruleNameRepository.save(trade);		
	}
	
	public void deleteRuleNameById(Integer id) {
		ruleNameRepository.deleteById(id);
	}
}
