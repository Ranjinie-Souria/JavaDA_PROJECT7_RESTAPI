package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="Id")
	private Integer id;

	@NotNull(message = "id cannot be empty")
	@DecimalMin(message = "must be a number", value = "0")
	@Column(name="CurveId")
	private Integer curveId;
	
	@Column(name="asOfDate")
	private Timestamp asOfDate;


	@Column(name="term")
	@NotNull(message = "term cannot be empty")
	@DecimalMin(message = "must be a number", value = "0")
	private Double term;


	@Column(name="value")
	@NotNull(message = "value cannot be empty")
	@DecimalMin(message = "must be a number", value = "0")
	private Double value;
	
	@Column(name="creationDate")
	private Timestamp creationDate;
	
	public CurvePoint(int id, double term, double value) {
		this.curveId = id;
		this.term = term;
		this.value = value;
	}
	
	
	
	public CurvePoint() {
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCurveId() {
		return curveId;
	}
	public void setCurveId(Integer curveId) {
		this.curveId = curveId;
	}
	public Timestamp getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(Timestamp asOfDate) {
		this.asOfDate = asOfDate;
	}
	public Double getTerm() {
		return term;
	}
	public void setTerm(Double term) {
		this.term = term;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	
	
}
