package com.springboot.pjt1.repository.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "사용자의 최저가 찜목록을 담는 dto")
public class WishList {
	@ApiModelProperty(notes = "찜목록 번호")
	private int wishNo;
	@ApiModelProperty(notes = "회원 ID")
	private String memberId;
	@ApiModelProperty(notes = "최저가 사이트명")
	private String siteName;
	@ApiModelProperty(notes = "최저가 사이트 링크")
	private String link;
	@ApiModelProperty(notes = "최저가 사이트의 상품명")
	private String productName;
	@ApiModelProperty(notes = "최저가 사이트의 상품가격")
	private String price;
	
	public int getWishNo() {
		return wishNo;
	}
	public void setWishNo(int wishNo) {
		this.wishNo = wishNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}
