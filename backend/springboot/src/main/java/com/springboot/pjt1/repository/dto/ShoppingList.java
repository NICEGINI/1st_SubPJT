package com.springboot.pjt1.repository.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "회원별 쇼핑리스트의 상품 목록을 담는 dto")
public class ShoppingList {
	@ApiModelProperty(notes = "회원 ID")
	private String memberId;
	@ApiModelProperty(notes = "쇼핑리스트 번호")
	private int shoppingListNo;
	@ApiModelProperty(notes = "쇼핑리스트 내 상품명")
	private String productName;
	@ApiModelProperty(notes = "쇼핑리스트 내 상품가격")
	private String productPrice;
	@ApiModelProperty(notes = "쇼핑리스트 내 상품 체크 여부")
	private boolean checked;
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public int getShoppingListNo() {
		return shoppingListNo;
	}
	public void setShoppingListNo(int shoppingListNo) {
		this.shoppingListNo = shoppingListNo;
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
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	@Override
	public String toString() {
		return "ShoppingList [memberId=" + memberId + ", shoppingListNo=" + shoppingListNo + ", productName="
				+ productName + ", productPrice=" + productPrice + ", checked=" + checked + "]";
	}
	
	

}
