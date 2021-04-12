package com.springboot.pjt1.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.pjt1.product.service.BestProductService;
import com.springboot.pjt1.repository.dto.BestProduct;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, allowCredentials = "true")
@RestController
@RequestMapping("/bestproduct")
public class BestProductController {
	
	@Autowired
	private BestProductService service;
	
	@ApiOperation(value = "모든 인기 상품 리스트를 반환한다.", response = List.class)
	@GetMapping
	public List<BestProduct> searchBestProduct() {
		return service.searchBestProduct();
	}


}
