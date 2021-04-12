package com.springboot.pjt1.repository.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "이번주 상품 정보를 담는 dto")
public class BestProduct {
	@ApiModelProperty(notes = "인기 순위")
	private int ranking;
	@ApiModelProperty(notes = "상품 번호")
	private int productNo;
	@ApiModelProperty(notes = "상품명")
	private String productName;
	@ApiModelProperty(notes = "상품 가격")
	private String productPrice;

	public int getRanking() {
		return ranking;
	}
	public void setRanking(int ranking) {
		this.ranking = ranking;
	}
	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	
}
