package com.hpetshop.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hpetshop.rest.service.ItemDescService;
import com.hpetshop.utils.Result;
/**
 * 
 * <ul>
 * <li>文件名称: ItemDescController</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2018</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月31日</li>
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
@RequestMapping("info")
public class ItemDescController {
	@Autowired
	private ItemDescService descService;
	@RequestMapping("/itemDescInfo/{itemId}")
	@ResponseBody
	public Result getItemDesc(@PathVariable long itemId) {
		Result itemDesc = descService.getItemDesc(itemId);
		return itemDesc;
	}
}
