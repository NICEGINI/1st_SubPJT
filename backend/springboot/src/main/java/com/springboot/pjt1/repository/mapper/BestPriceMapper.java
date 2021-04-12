package com.springboot.pjt1.repository.mapper;

import java.util.List;

import com.springboot.pjt1.repository.dto.BestPrice;

public interface BestPriceMapper {
	
	List<BestPrice> selectBestPriceByProductNo(int productNo);
	
	int deleteBestPriceByProductNo(int productNo);
	
	int insertBestPrice(BestPrice bestPrice);
	
	int selectCountBestPrice(int productNo);
}
