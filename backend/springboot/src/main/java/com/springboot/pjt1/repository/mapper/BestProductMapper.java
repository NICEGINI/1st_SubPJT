package com.springboot.pjt1.repository.mapper;

import java.util.List;

import com.springboot.pjt1.repository.dto.BestProduct;

public interface BestProductMapper {
	
	List<BestProduct> selectBestProduct();

}
