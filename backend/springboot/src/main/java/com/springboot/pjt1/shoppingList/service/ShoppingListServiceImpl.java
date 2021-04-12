package com.springboot.pjt1.shoppingList.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.pjt1.repository.dto.ShoppingList;
import com.springboot.pjt1.repository.mapper.ShoppingListMapper;

@Service
public class ShoppingListServiceImpl implements ShoppingListService   {

	@Autowired
	ShoppingListMapper mapper;
	
	@Override
	public List<ShoppingList> selectShoppingListByMemberId(String MemeberId) {
		return mapper.selectShoppingListByMemberId(MemeberId);
	}

	@Override
	public int insertShoppingList(ShoppingList shoppingList) {
		mapper.insertShoppingList(shoppingList);
		return shoppingList.getShoppingListNo();
	}

	@Override
	public boolean updateShoppingList(ShoppingList shoppingList) {
		return mapper.updateShoppingList(shoppingList) == 1;
	}

	@Override
	public boolean deleteShoppingListByShoppingListNo(int shoppingListNo) {
		return mapper.deleteShoppingListByShoppingListNo(shoppingListNo) == 1;
	}
	
}
