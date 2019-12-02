package com.yanzheng.o2o.dto;

import java.util.List;

import com.yanzheng.o2o.entity.Shop;
import com.yanzheng.o2o.enums.ShopStateEnum;

public class ShopExecution {
	//state of result
	private int state;
	
	//state info
	private String stateInfo;
	
	//num of shops
	private int count;
	
	private Shop shop;
	
	//list of shop
	private List<Shop> shopList;
	
	public ShopExecution() {
		
		
	}
	
	// constructor for failure
	public ShopExecution(ShopStateEnum stateEnum ) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	// constructor for success
	public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop;
	}
	
	// constructor for success
	public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shopList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
}
