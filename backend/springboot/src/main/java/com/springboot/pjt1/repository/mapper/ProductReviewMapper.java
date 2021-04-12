package com.springboot.pjt1.repository.mapper;

import java.util.List;

import com.springboot.pjt1.repository.dto.ProductReview;

public interface ProductReviewMapper {

	List<ProductReview> selectProductReviewByProductNo(int productNo);
	
	int insertProductReview(ProductReview productReview);
	
	int updateProductReviewByCommentNo(ProductReview productReview);
	
	int deleteProductReviewByCommentNo(int commentNo);
	
}
