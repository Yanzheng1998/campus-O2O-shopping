package com.yanzheng.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yanzheng.o2o.BaseTest;
import com.yanzheng.o2o.dto.ShopExecution;
import com.yanzheng.o2o.entity.Area;
import com.yanzheng.o2o.entity.PersonInfo;
import com.yanzheng.o2o.entity.Shop;
import com.yanzheng.o2o.entity.ShopCategory;
import com.yanzheng.o2o.enums.ShopStateEnum;
import com.yanzheng.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	
	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(3L);
		shopCondition.setShopCategory(sc);
		ShopExecution shopExecution = shopService.getShopList(shopCondition, 1, 2);
		System.out.println("shop list size: " + shopExecution.getShopList().size());
		System.out.println("shop count: " + shopExecution.getCount());
	}
	
	@Test
	@Ignore
	public void testModifyShop() throws ShopOperationException, FileNotFoundException {
		Shop shop = shopService.getByShopId(15L);
		shop.setShopName("shop name after modification");
		File shopImg = new File("/Users/yanzheng/image/supermarket.jpg");
		InputStream is = new FileInputStream(shopImg);
		ShopExecution shopExecution = shopService.modifyShop(shop, is, "supermarket.jpg");
		System.out.println("new image address is: " + shopExecution.getShop().getShopImg());
	}
	
	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopName("test shop3");
		shop.setShopDesc("test3");
		shop.setPhone("test3");
		shop.setShopAddr("test3");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("pending");
		File shopImg = new File("/Users/yanzheng/image/supermarket.jpg");
		InputStream inputStream = new FileInputStream(shopImg);
		ShopExecution shopExecution = shopService.addShop(shop, inputStream, shopImg.getName());
		assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
	}
}
