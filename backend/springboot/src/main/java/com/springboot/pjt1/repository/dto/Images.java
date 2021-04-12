package com.springboot.pjt1.repository.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "이미지 처리를 위한 정보를 담는 dto")
public class Images {
	@ApiModelProperty(notes = "이미지를 담고있는 byte 배열")
	private byte[] image;
	@ApiModelProperty(notes = "처리된 이미지라면 T, 처리되지 않은 이미지라면 F")
	private char state;
	@ApiModelProperty(notes = "시각장애인용 이미지라면 1, 비장애인용 이미지라면 2")
	private int num;
	@ApiModelProperty(notes = "이미지 처리 요청을 한 회원 ID")
	private String memberId;
	@ApiModelProperty(notes = "찾는 물품")
	private String search;
	
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public char getState() {
		return state;
	}
	public void setState(char state) {
		this.state = state;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
}
