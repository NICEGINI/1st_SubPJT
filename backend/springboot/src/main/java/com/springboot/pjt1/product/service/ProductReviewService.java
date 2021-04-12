package com.springboot.pjt1.product.service;

import java.util.List;

import com.springboot.pjt1.repository.dto.ProductReview;

public interface ProductReviewService {

	List<ProductReview> selectProductReviewByProductNo(int ProductNo);
	
	boolean insertProductReview(ProductReview productReview);
	
	boolean updateProductReviewByCommentNo(ProductReview productReview);
	
	boolean deleteProductReviewByCommentNo(int commentNo);

}