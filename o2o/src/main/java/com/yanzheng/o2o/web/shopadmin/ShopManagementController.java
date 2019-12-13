package com.yanzheng.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanzheng.o2o.dto.ShopExecution;
import com.yanzheng.o2o.entity.Area;
import com.yanzheng.o2o.entity.PersonInfo;
import com.yanzheng.o2o.entity.Shop;
import com.yanzheng.o2o.entity.ShopCategory;
import com.yanzheng.o2o.enums.ShopStateEnum;
import com.yanzheng.o2o.exceptions.ShopOperationException;
import com.yanzheng.o2o.service.AreaService;
import com.yanzheng.o2o.service.ShopService;
import com.yanzheng.o2o.service.impl.ShopCategoryService;
import com.yanzheng.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ShopCategoryService shopCategoryService;
	
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value="/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	@RequestMapping(value="/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//1. accept and deserialize info
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg","cannot upload null image");
			return modelMap;
		}
		
		//2. register shop
		if (shop != null && shopImg != null) {
			PersonInfo owner = new PersonInfo();
			owner.setUserId(1L);
			shop.setOwner(owner);
			ShopExecution shopExecution;
			try {
				shopExecution = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename() );
				if (shopExecution.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("succes", true);
				} else {
					modelMap.put("succes", false);
					modelMap.put("errMsg", shopExecution.getStateInfo());
				}
			} catch (ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg","please enter shop information");
			return modelMap;
		}
		//3. return 
	}
	
//	private static void inputStreamToFile(InputStream inputStream, File file) {
//		FileOutputStream outputStream = null;
//		try {
//			outputStream = new FileOutputStream(file);
//			int bytesRead = 0;
//			byte[] buffer = new byte[1024];
//			while ((bytesRead = inputStream.read(buffer)) != - 1) {
//				outputStream.write(buffer,0,bytesRead);
//			}
//		} catch (Exception e) {
//			throw new RuntimeException("invoke inputStreamToFile causes error: " + e.getMessage());
//		} finally {
//			try {
//				if (outputStream != null) {
//					outputStream.close();
//				}
//				if (inputStream != null) {
//					inputStream.close();
//				}
//			} catch (IOException e) {
//				throw new RuntimeException("InputStreamToFile closing IO causes error: " + e.getMessage());
//			}
//		}
//	}
}
