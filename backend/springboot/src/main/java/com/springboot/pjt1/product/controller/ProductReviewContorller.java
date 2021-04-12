package com.springboot.pjt1.product.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.pjt1.product.service.ProductReviewService;
import com.springboot.pjt1.repository.dto.ProductReview;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, allowCredentials = "true")
@RestController
@RequestMapping("/product/review")
public class ProductReviewContorller {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private ProductReviewService service;
	
	@ApiOperation(value = "리뷰를 등록한다. 그리고 DB 입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> insertProductReview(ProductReview productReview) {
		if(service.insertProductReview(productReview)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "리뷰를 수정한다. 그리고 DB 입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> searchProductByName(ProductReview productReview) throws IOException {
		if(service.updateProductReviewByCommentNo(productReview)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "리뷰를 삭제한다. 그리고 DB 입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping
	public ResponseEntity<String> deleteProductReviewByCommentNo(@RequestParam int commentNo) {
		if(service.deleteProductReviewByCommentNo(commentNo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "상품 번호에 해당하는 리뷰 목록을 가져온다.", response = List.class)
	@GetMapping
	public List<ProductReview> selectProductReviewByProductNo(@RequestParam int productNo) {
		return service.selectProductReviewByProductNo(productNo);	
	}
	
}
