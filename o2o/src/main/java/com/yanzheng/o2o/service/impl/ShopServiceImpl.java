package com.yanzheng.o2o.service.impl;

import java.io.File;
import java.util.Date;

import org.apache.catalina.tribes.group.interceptors.StaticMembershipInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yanzheng.o2o.dao.ShopDao;
import com.yanzheng.o2o.dto.ShopExecution;
import com.yanzheng.o2o.entity.Shop;
import com.yanzheng.o2o.enums.ShopStateEnum;
import com.yanzheng.o2o.exceptions.ShopOperationException;
import com.yanzheng.o2o.service.ShopService;
import com.yanzheng.o2o.util.ImageUtil;
import com.yanzheng.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;
	
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, File shopImg) {
		// check null
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		//TODO: check null for shop`s fields
		
		try {
			// set initial values
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			// insert shop info
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				throw new ShopOperationException("Fail to create shop");
			} else {
				if (shopImg != null) {
					// store img
					try {
						addShopImg(shop, shopImg);
					} catch (Exception e) {
						throw new ShopOperationException("addShopInfo error: " + e.getMessage());
					}
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("Fail to update shop image");
					} 
				}
				
			}
			
		} catch (Exception e) {
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}

	private void addShopImg(Shop shop, File shopImg) {
		// get the relative path of shop image folder
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		
		String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
		
		shop.setShopImg(shopImgAddr);
	}
}
