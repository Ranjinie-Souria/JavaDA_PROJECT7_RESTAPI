package com.nnk.springboot.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "rating")
public class Rating {
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="Id")
	private Integer id;
	
	@NotEmpty(message = "Cannot be empty")
	@Column(name="moodysRating")
	private String moodysRating;
	
	@NotEmpty(message = "Cannot be empty")
	@Column(name="sandPRating")
	private String sandPRating;
	
	@NotEmpty(message = "Cannot be empty")
	@Column(name="fitchRating")
	private String fitchRating;
	
	@Column(name="orderNumber")
	@NotNull(message = "Cannot be empty")
	@DecimalMin(message = "must be a number", value = "0")
	private Integer orderNumber;
	
	public Rating(String moodysRating, String sandPRating, String fitchRating, int orderNumber) {
		this.moodysRating = moodysRating;
		this.sandPRating = sandPRating;
		this.fitchRating = fitchRating;
		this.orderNumber = orderNumber;		
	}
	
	
	
	public Rating() {
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMoodysRating() {
		return moodysRating;
	}
	public void setMoodysRating(String moodysRating) {
		this.moodysRating = moodysRating;
	}
	public String getSandPRating() {
		return sandPRating;
	}
	public void setSandPRating(String sandPRating) {
		this.sandPRating = sandPRating;
	}
	public String getFitchRating() {
		return fitchRating;
	}
	public void setFitchRating(String fitchRating) {
		this.fitchRating = fitchRating;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	

}
