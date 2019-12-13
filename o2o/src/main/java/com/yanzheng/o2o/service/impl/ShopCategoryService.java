package com.yanzheng.o2o.service.impl;

import java.util.List;

import com.yanzheng.o2o.entity.ShopCategory;

public interface ShopCategoryService {
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
