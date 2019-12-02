package com.yanzheng.o2o.enums;

public enum ShopStateEnum {
	CHECK(0, "under censor"),

	OFFLINE(-1, "illegal shop"),

	SUCCESS(1, "operation succeeded"),

	PASS(2, "verified"),

	INNER_ERROR(-1001, "Internal System Error"),

	NULL_SHOPID(-1002, "shopId is null"),
	
	NULL_SHOP(-1003, "shop is null")

	;

	private int state;

	private String stateInfo;

	private ShopStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public static ShopStateEnum stateOf(int state) {
		for (ShopStateEnum stateEnum : values()) {
			if (stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

}
