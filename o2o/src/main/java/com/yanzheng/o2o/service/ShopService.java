package com.yanzheng.o2o.service;

import java.io.InputStream;

import com.yanzheng.o2o.dto.ShopExecution;
import com.yanzheng.o2o.entity.Shop;
import com.yanzheng.o2o.exceptions.ShopOperationException;

public interface ShopService {
	ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;
}
