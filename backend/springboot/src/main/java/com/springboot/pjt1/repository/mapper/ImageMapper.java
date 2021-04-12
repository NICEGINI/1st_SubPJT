package com.springboot.pjt1.repository.mapper;

import com.springboot.pjt1.repository.dto.Images;
import com.springboot.pjt1.repository.dto.ImagesResults;

public interface ImageMapper {
	
	void insertImage(Images image);
	
	void insertImageWithItemName(Images image);
	
	ImagesResults selectImageResultsByMemberId(String memberId);
	
	int deleteImagesResultsByMemberId(String memberId);
	
}
