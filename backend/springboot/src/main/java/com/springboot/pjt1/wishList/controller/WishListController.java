package com.springboot.pjt1.wishList.controller;

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

import com.springboot.pjt1.repository.dto.WishList;
import com.springboot.pjt1.wishList.service.WishListService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, allowCredentials = "true")
@RestController
@RequestMapping("/wishList")
public class WishListController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	WishListService service;

	@ApiOperation(value = "사용자의 모든 찜 목록을 반환한다.", response = List.class)
	@GetMapping
	public List<WishList> searchWishListByMemberId(String memberId) {
		return service.searchWishListByMemberId(memberId);
	}

	@ApiOperation(value = "찜 목록에 항목을 추가한다. DB 입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> addWishListByMemberId(WishList wishList) {
		if (service.addWishListByMemberId(wishList)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "사용자의 찜 목록 중 한 항목을 삭제한다. DB 입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping
	public ResponseEntity<String> removeWishListByNo(int wishNo) {
		if (service.removeWishListByNo(wishNo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
