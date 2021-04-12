package com.springboot.pjt1.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.pjt1.product.service.MyProductService;
import com.springboot.pjt1.repository.dto.MyProduct;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, allowCredentials = "true")
@RestController
@RequestMapping("/myProduct")
public class MyProductController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	MyProductService service;
	
	@ApiOperation(value = "회원 아이디에 해당하는 회원이 저장해 놓은 상품 리스트를 반환한다.", response = List.class)
	@GetMapping
	List<MyProduct> searchMyProductByMemberId(String id) {
		return service.searchMyProductByMemberId(id);
	}
	
	@ApiOperation(value = "새로운 상품 정보를 입력한다. DB 입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	ResponseEntity<String> addMyProduct(MyProduct myProduct) {
		if(service.addMyProduct(myProduct)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "즐겨찾기 번호로 상품 정보를 삭제한다. DB 입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping
	ResponseEntity<String> removeMyProduct(int myProductNo) {
		if(service.removeMyProduct(myProductNo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}
