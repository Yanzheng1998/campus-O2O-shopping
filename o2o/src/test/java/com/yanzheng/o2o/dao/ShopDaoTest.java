package com.yanzheng.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.eclipse.jdt.internal.compiler.problem.ShouldNotImplement;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yanzheng.o2o.BaseTest;
import com.yanzheng.o2o.entity.Area;
import com.yanzheng.o2o.entity.PersonInfo;
import com.yanzheng.o2o.entity.Shop;
import com.yanzheng.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDao shopDao;
	@Test
	@Ignore
	public void testInsertShop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopName("test shop");
		shop.setShopDesc("test");
		shop.setPhone("test");
		shop.setShopAddr("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("pending");
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(2L);
		shop.setShopDesc("new desc");
		shop.setPhone("new phone");
		shop.setShopAddr("new addr");
		shop.setLastEditTime(new Date());
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1,effectedNum);
	}
}
