package com.springboot.pjt1.repository.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "상품 리뷰를 담는 dto")
public class ProductReview {
	@ApiModelProperty(notes = "리뷰 번호")
	private int commentNo;
	@ApiModelProperty(notes = "상품 번호")
	private int productNo;
	@ApiModelProperty(notes = "회원 ID")
	private String memberId;
	@ApiModelProperty(notes = "회원명")
	private String memberName;
	@ApiModelProperty(notes = "별점")
	private String score;
	@ApiModelProperty(notes = "리뷰 내용")
	private String comment;
	@ApiModelProperty(notes = "리뷰 등록 날짜")
	private String date;
	
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
