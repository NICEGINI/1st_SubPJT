package com.springboot.pjt1.product.service;

import java.util.List;

import com.springboot.pjt1.repository.dto.MyProduct;

public interface MyProductService {

	List<MyProduct> searchMyProductByMemberId(String id);

	boolean addMyProduct(MyProduct myProduct);

	boolean removeMyProduct(int myProductNo);

}