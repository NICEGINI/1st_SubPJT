package com.springboot.pjt1.repository.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "사용자가 즐겨찾기에 추가한 상품 dto")
public class MyProduct {
	@ApiModelProperty(notes = "즐겨찾기 번호")
	private int myProductNo;
	@ApiModelProperty(notes = "회원 ID")
	private String memberId;
	@ApiModelProperty(notes = "상품명")
	private String productName;
	@ApiModelProperty(notes = "상품가격")
	private String productPrice;
	
	public int getMyProductNo() {
		return myProductNo;
	}
	public void setMyProductNo(int myProductNo) {
		this.myProductNo = myProductNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
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
