package com.springboot.pjt1.repository.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "사이트 최저가 정보를 담는 dto")
public class BestPrice {
	@ApiModelProperty(notes = "최저가 번호")
	private int no;
	@ApiModelProperty(notes = "상품번호")
	private int productNo;
	@ApiModelProperty(notes = "최저가 사이트명")
	private String siteName;
	@ApiModelProperty(notes = "최저가 사이트 내 상품명")
	private String productName;
	@ApiModelProperty(notes = "상품가격")
	private String price;
	@ApiModelProperty(notes = "최저가 사이트 링크")
	private String link;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
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
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	@Override
	public String toString() {
		return "BestPrice [no=" + no + ", productNo=" + productNo + ", siteName=" + siteName + ", productName="
				+ productName + ", price=" + price + ", link=" + link + "]";
	}
	
}
