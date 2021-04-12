package com.springboot.pjt1.repository.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "이번주 상품 정보를 담는 dto")
public class ImagesResults {
	@ApiModelProperty(notes = "이미지 결과 번호")
	private int id;
	@ApiModelProperty(notes = "이미지 처리 요청을 한 회원 ID")
	private int member_id;
	@ApiModelProperty(notes = "모드번호")
	private String num;
	@ApiModelProperty(notes = "디텍션 결과")
	private String result;
	@ApiModelProperty(notes = "날짜")
	private String reg_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getReg_time() {
		return reg_time;
	}
	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}
	@Override
	public String toString() {
		return "imagesResults [id=" + id + ", member_id=" + member_id + ", num=" + num + ", result=" + result
				+ ", reg_time=" + reg_time + "]";
	}
	
	
	
}
