package com.springboot.pjt1.product.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.pjt1.repository.dto.Product;
import com.springboot.pjt1.repository.mapper.BestPriceMapper;
import com.springboot.pjt1.repository.mapper.ProductMapper;
import com.springboot.pjt1.repository.mapper.ProductReviewMapper;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductMapper mapper;
	
	@Autowired
	ProductReviewMapper reviewMapper;
	
	@Autowired
	BestPriceMapper bpMapper;
	
	@Autowired
	BestPriceService bpService;
	
	@Override
	public Map<String, Object> searchProductDetail(String name) throws IOException {
		Map<String, Object> map = new HashMap<>();
		Product product = selectProductByExactName(name);
		map.put("product", product);
		if(product != null) {
			map.put("review", reviewMapper.selectProductReviewByProductNo(product.getProductNo()));
			
			if(bpMapper.selectCountBestPrice(product.getProductNo()) > 0) {
				map.put("bestPrice", bpMapper.selectBestPriceByProductNo(product.getProductNo()));
				
				bpService.deleteBestPriceByProductNo(product.getProductNo());
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							bpService.insertBestPrice(product.getProductNo());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
				System.out.println("안녕");
			}
			else {
				bpService.insertBestPrice(product.getProductNo());
				map.put("bestPrice", bpMapper.selectBestPriceByProductNo(product.getProductNo()));
			}
			
		}
		else {
			map.put("review", null);
			map.put("bestPrice", null);
		}
		return map;
	}
	
	@Override
	public List<Product> searchProduct() {
		return mapper.selectProduct();
	}
	@Override
	public List<Product> searchProductByName(String name) {	// name을 포함한 이름을 가진 모든 product
		return mapper.selectProductByName(name);
	}
	@Override
	public Product selectProductByExactName(String name) {	// 정확히 상품이름이 name인 product
		return mapper.selectProductByExactName(name);
	}
	@Override
	public List<Product> searchProductByCategory(String category) {
		return mapper.selectProductByCategory(category);
	}
	@Override
	public List<Product> selectProductByNameAndCategory(Product product) {
		return mapper.selectProductByNameAndCategory(product);
	}
	
	@Override
	public Product selectProductByProductNo(int productNo) {
		return mapper.selectProductByProductNo(productNo);
	}


}
