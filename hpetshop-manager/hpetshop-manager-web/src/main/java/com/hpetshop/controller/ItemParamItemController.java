package com.hpetshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hpetshop.service.ItemParamItemService;

/**
 * 
 * <ul>
 * <li>文件名称: ItemParamItemController</li>
 * <li>文件描述:用作前台显示商品规格</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月5日</li>
 * </ul>
 * <ul>
 * <li>修改记录:</li>
 * <li>版 本 号:</li>
 * <li>修改日期:</li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 * 
 * @author wushaochuan
 * @version 1.0.0
 */
@Controller
public class ItemParamItemController {
	@Autowired
	ItemParamItemService itemParamItemService;

	/**
	 * 用于展示商品规格
	 * 
	 * @param itemId
	 * @param model
	 * @return
	 */
	@RequestMapping("show/item/{itemId}")
	public String getItemParamItemById(@PathVariable Long itemId, Model model) {
		String html = itemParamItemService.getItemParamItemById(itemId);
		model.addAttribute("itemParam", html);
		return "item";
	}
}
