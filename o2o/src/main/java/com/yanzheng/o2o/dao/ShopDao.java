package com.yanzheng.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanzheng.o2o.entity.Shop;

public interface ShopDao {
	
	/**
	 * paging query
	 * 
	 * @param shopCondtion
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex, 
			@Param("pageSize") int pageSize);
	
	/**
	 * 
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
	
	/**
	 * 
	 * 
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);
	
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
