package com.yanzheng.o2o.service;

import java.io.File;

import com.yanzheng.o2o.dto.ShopExecution;
import com.yanzheng.o2o.entity.Shop;

public interface ShopService {
	ShopExecution addShop(Shop shop, File shopImg);
}
