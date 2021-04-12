package com.springboot.pjt1.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.pjt1.repository.dto.MyProduct;
import com.springboot.pjt1.repository.mapper.MyProductMapper;

@Service
public class MyProductServiceImpl implements MyProductService {

	@Autowired
	MyProductMapper mapper;
	
	@Override
	public List<MyProduct> searchMyProductByMemberId(String id) {
		 return mapper.selectMyProductByMemberId(id);
	}
	
	@Override
	@Transactional
	public boolean addMyProduct(MyProduct myProduct) {
		return mapper.insertMyProduct(myProduct) == 1;
	}
	
	@Override
	@Transactional
	public boolean removeMyProduct(int myProductNo) {
		return mapper.deleteMyProduct(myProductNo) == 1;
	}
	
}
