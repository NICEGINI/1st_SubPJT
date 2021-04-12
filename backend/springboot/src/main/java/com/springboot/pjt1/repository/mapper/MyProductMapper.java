package com.springboot.pjt1.repository.mapper;

import java.util.List;

import com.springboot.pjt1.repository.dto.MyProduct;

public interface MyProductMapper {
	
	List<MyProduct> selectMyProductByMemberId(String id);
	
	int insertMyProduct(MyProduct myProduct);
	
	int deleteMyProduct(int myProductNo);
	
}
