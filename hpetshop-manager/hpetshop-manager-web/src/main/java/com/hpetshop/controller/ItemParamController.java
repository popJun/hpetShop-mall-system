package com.hpetshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hpetshop.pojo.HpItemParam;
import com.hpetshop.service.ItemParamService;
import com.hpetshop.utils.Result;

/**
 * 
 * <ul>
 * <li>文件名称: ItemParamController</li>
 * <li>文件描述:对商品规格表进行操作</li>
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
public class ItemParamController {

	@Autowired
	ItemParamService itemParamService;

	/**
	 * 用于判断该分类下是否以创建商品规格
	 * 
	 * @param itemCatId
	 * @return
	 */
	@RequestMapping("item/param/query/itemcatid/{itemCatId}")
	@ResponseBody
	public Result findItemParamByItemCatId(@PathVariable Long itemCatId) {
		Result result = itemParamService.findItemParamByItemCatId(itemCatId);
		return result;
	}

	@RequestMapping("item/param/save/{itemCatId}")
	@ResponseBody
	public Result insertItemParam(@PathVariable Long itemCatId, String paramData) {
		HpItemParam hpItemParam = new HpItemParam();
		hpItemParam.setItemCatId(itemCatId);
		hpItemParam.setParamData(paramData);
		Result result = itemParamService.insertItemParam(hpItemParam);
		return result;
	}
}
