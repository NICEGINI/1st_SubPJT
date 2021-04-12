package com.springboot.pjt1.wishList.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.pjt1.repository.dto.WishList;
import com.springboot.pjt1.repository.mapper.WishListMapper;

@Service
public class WishListServiceImpl implements WishListService {
	
	@Autowired
	WishListMapper mapper;
	
	@Override
	public List<WishList> searchWishListByMemberId(String memberId) {
		return mapper.selectWishListByMemberId(memberId);
	}

	@Override
	public boolean addWishListByMemberId(WishList wishList) {
		return mapper.insertWishList(wishList) == 1;
	}
	
	@Override
	public boolean removeWishListByNo(int wishNo) {
		return mapper.deleteWishListByNo(wishNo) == 1;
	}
	
}
