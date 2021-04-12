package com.springboot.pjt1.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.pjt1.repository.dto.BestProduct;
import com.springboot.pjt1.repository.mapper.BestProductMapper;

@Service
public class BestProductServiceImpl implements BestProductService {
	@Autowired
	BestProductMapper mapper;
	
	@Override
	public List<BestProduct> searchBestProduct() {
		return mapper.selectBestProduct();
	}
}
