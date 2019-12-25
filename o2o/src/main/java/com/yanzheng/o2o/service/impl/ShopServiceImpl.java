package com.yanzheng.o2o.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

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
import com.yanzheng.o2o.util.PageCalculator;
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
	
	@Override
	public Shop getByShopId(long shopId) {
		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName)
			throws ShopOperationException {
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		} else {
			//1. determine if image need handle
			try {
				if (shopImgInputStream != null && fileName != null && !"".equals(fileName)) {
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					if (tempShop.getShopImg() != null) {
						ImageUtil.deleteFileOrPath(tempShop.getShopImg());
					}
					addShopImg(shop, shopImgInputStream, fileName);
				}
				//2. update shop info
				shop.setLastEditTime(new Date());
				int effectedNum = shopDao.updateShop(shop);
				if (effectedNum <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				} else {
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS, shop);
				} 
			} catch (Exception e) {
				throw new ShopOperationException("modifyShop error: "+ e.getMessage());
			}
		}
	}
	
	private void addShopImg(Shop shop, InputStream shopImgInputStream, String fileName) {
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream, fileName, dest);
		shop.setShopImg(shopImgAddr);
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution shopExecution = new ShopExecution();
		if (shopList != null) {
			shopExecution.setShopList(shopList);
			shopExecution.setCount(count);
		} else {
			shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return shopExecution;
	}
}
