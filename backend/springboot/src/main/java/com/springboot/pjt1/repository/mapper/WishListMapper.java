package com.springboot.pjt1.repository.mapper;

import java.util.List;

import com.springboot.pjt1.repository.dto.WishList;

public interface WishListMapper {
	
	public List<WishList> selectWishListByMemberId(String memberId);

	public int insertWishList(WishList wishList);
	
	public int deleteWishListByNo(int wishNo);
	
}
