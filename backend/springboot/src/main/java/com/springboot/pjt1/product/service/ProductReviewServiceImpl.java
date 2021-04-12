package com.springboot.pjt1.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.pjt1.repository.dto.ProductReview;
import com.springboot.pjt1.repository.mapper.ProductReviewMapper;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

	@Autowired
	ProductReviewMapper mapper;
	
	@Override
	public List<ProductReview> selectProductReviewByProductNo(int ProductNo) {
		return mapper.selectProductReviewByProductNo(ProductNo);
	}

	@Override
	public boolean insertProductReview(ProductReview productReview) {
		return mapper.insertProductReview(productReview) == 1;
	}

	@Override
	public boolean updateProductReviewByCommentNo(ProductReview productReview) {
		return mapper.updateProductReviewByCommentNo(productReview) == 1;
	}

	@Override
	public boolean deleteProductReviewByCommentNo(int commentNo) {
		return mapper.deleteProductReviewByCommentNo(commentNo) == 1;
	}
	
}
