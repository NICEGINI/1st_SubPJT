package com.springboot.pjt1.shoppingList.controller;

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

import com.springboot.pjt1.repository.dto.ShoppingList;
import com.springboot.pjt1.shoppingList.service.ShoppingListService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, allowCredentials = "true")
@RestController
@RequestMapping("/shoppingList")
public class ShoppingListContorller {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private ShoppingListService service;
	
	@ApiOperation(value = "쇼핑리스트 상품 등록. DB 입력 성공여부에 따라 해당 'shoppingListNo' 또는 '0' 값을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<Integer> insertShoppingList(ShoppingList shoppingList) {
		int shoppingListNo = service.insertShoppingList(shoppingList);
		if(shoppingListNo != 0) {
			return new ResponseEntity<Integer>(shoppingListNo, HttpStatus.OK);
		}
		return new ResponseEntity<Integer>(0, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "쇼핑리스트 상품 수정. DB 입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> updateShoppingList(ShoppingList shoppingList) throws IOException {
		if(service.updateShoppingList(shoppingList)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "쇼핑리스트 삭제. DB 입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping
	public ResponseEntity<String> deleteProductReviewByCommentNo(@RequestParam int shoppingListNo) {
		if(service.deleteShoppingListByShoppingListNo(shoppingListNo)) {
			
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "회원 ID에 해당하는 쇼핑리스트 상품목록을 가져온다.", response = List.class)
	@GetMapping
	public List<ShoppingList> selectShoppingListByMemberId(@RequestParam String memberId) {
		return service.selectShoppingListByMemberId(memberId);	
	}
	
}
