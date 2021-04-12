package com.springboot.pjt1.repository.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "상품 목록의 상품 정보를 담는 dto")
public class Product {
	@ApiModelProperty(notes = "상품 번호")
	private int productNo;
	@ApiModelProperty(notes = "상품명")
	private String productName;
	@ApiModelProperty(notes = "상품 가격")
	private String productPrice;
	@ApiModelProperty(notes = "상품 분류")
	private String category;
	@ApiModelProperty(notes = "이마트 상품 리뷰 링크")
	private String reviewLink;
	@ApiModelProperty(notes = "저장된 상품 이미지 파일명")
	private String imageName;
	
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getReviewLink() {
		return reviewLink;
	}
	public void setReviewLink(String reviewLink) {
		this.reviewLink = reviewLink;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Product(String productName, String category) {
		this.productName = productName;
		this.category = category;
	}
	@Override
	public String toString() {
		return "Product [productNo=" + productNo + ", productName=" + productName + ", productPrice=" + productPrice
				+ ", category=" + category + ", reviewLink=" + reviewLink + ", imageName=" + imageName + "]";
	}
	
}
