package com.yanzheng.o2o.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

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
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException{
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
				if (shopImgInputStream != null) {
					// store img
					try {
						addshopImgInputStream(shop, shopImgInputStream, fileName);
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

	private void addshopImgInputStream(Shop shop, InputStream shopImgInputStream, String fileName) {
		// get the relative path of shop image folder
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		
		String shopImgInputStreamAddr = ImageUtil.generateThumbnail(shopImgInputStream, fileName, dest);
		
		shop.setShopImg(shopImgInputStreamAddr);
	}
}
