package com.yanzheng.o2o.dao;

import com.yanzheng.o2o.entity.Shop;

public interface ShopDao {
	/**
	 * add shop
	 * @param shop
	 * @return 
	 */
	int insertShop(Shop shop);
	
	/**
	 * update shop info
	 * 
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
}
