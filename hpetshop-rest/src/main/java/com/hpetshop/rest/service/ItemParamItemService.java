package com.hpetshop.rest.service;

import com.hpetshop.utils.Result;

/**
 * 
 * <ul>
 * <li>文件名称: ItemParamItemService</li>
 * <li>文件描述:对商品规格表进行操作</li>
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
public interface ItemParamItemService {
	/**
	 * 获取商品规格信息
	 * 
	 * @param itemId
	 * @return
	 */
	public Result getItemParamItem(Long itemId);
}
