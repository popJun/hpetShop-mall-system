package com.hpetshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hpetshop.dto.TypeNodeDTO;
import com.hpetshop.service.ItemCatService;

/**
 * 
 * <ul>
 * <li>文件名称: ItemCatController</li>
 * <li>文件描述:用于显示商品类别</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月1日</li>
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
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping("item/cat/list")
	@ResponseBody
	public List<TypeNodeDTO> getItemCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<TypeNodeDTO> typeNodeDTOs = itemCatService.getItemCatList(parentId);
		return typeNodeDTOs;
	}
}
