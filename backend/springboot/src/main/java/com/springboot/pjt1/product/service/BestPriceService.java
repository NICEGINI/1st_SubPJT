package com.springboot.pjt1.product.service;

import java.io.IOException;
import java.util.List;

import com.springboot.pjt1.repository.dto.BestPrice;

public interface BestPriceService {

	List<BestPrice> selectBestPriceByProductNo(int productNo);

	boolean deleteBestPriceByProductNo(int productNo);

	void insertBestPrice(int productNo) throws IOException;

	List<BestPrice> getBestPriceList(String name) throws IOException;

}