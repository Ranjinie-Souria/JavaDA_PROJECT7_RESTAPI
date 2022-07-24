package com.nnk.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@Service
public class CurvePointService {
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	public List<CurvePoint> getCurvePoints() {
		return curvePointRepository.findAll();
	}
	
	public Optional<CurvePoint> getCurvePointById(Integer id) {
		return curvePointRepository.findById(id);
	}
	
	public CurvePoint saveCurvePoint(CurvePoint curvePoint) {
		return curvePointRepository.save(curvePoint);		
	}
	
	public void deleteCurvePointById(Integer id) {
		curvePointRepository.deleteById(id);
	}

}
