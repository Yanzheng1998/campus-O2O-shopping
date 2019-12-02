package com.yanzheng.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yanzheng.o2o.BaseTest;
import com.yanzheng.o2o.entity.Area;

public class AreaServiceTest extends BaseTest {
	@Autowired
	private AreaService areaService;

	@Test
	public void testGetAreaList() {
		List<Area> arealist = areaService.getAreaList();
		assertEquals("West Campus", arealist.get(0).getAreaName());
	}
}
