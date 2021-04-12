package com.springboot.pjt1.shoppingList.service;

import java.util.List;

import com.springboot.pjt1.repository.dto.ShoppingList;

public interface ShoppingListService {

	List<ShoppingList> selectShoppingListByMemberId(String MemeberId);

	int insertShoppingList(ShoppingList shoppingList);

	boolean updateShoppingList(ShoppingList shoppingList);

	boolean deleteShoppingListByShoppingListNo(int shoppingListNo);

}