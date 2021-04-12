package com.springboot.pjt1.image.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	// 일반인과 시각장애인을 위한 상품 확인!! 번호로는 1번 3번
	String getProductName(MultipartFile file, String memberId);

	// 시각 장애인을 위한 상품 위치 찾기!! 번호로는 2번
	String getDirection(MultipartFile file, String itemName, String memberId);

}