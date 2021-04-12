package com.springboot.pjt1.repository.mapper;

import java.util.List;

import com.springboot.pjt1.repository.dto.ShoppingList;

public interface ShoppingListMapper {
	
	public List<ShoppingList> selectShoppingListByMemberId(String MemeberId);
	
	public int insertShoppingList(ShoppingList shoppingList);
	
	public int updateShoppingList(ShoppingList shoppingList);
	
	public int deleteShoppingListByShoppingListNo(int shoppingListNo);
	
}
