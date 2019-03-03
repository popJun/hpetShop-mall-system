package com.hpetshop.service;

/**
 * 
 * <ul>
 * <li>文件名称: ItemParamItemService</li>
 * <li>文件描述:用于对ItemParamItem表商品和商品规格映射表进行操作</li>
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

public interface ItemParamItemService {
	/**
	 * 用于前端显示规格参数
	 * 
	 * @param id
	 * @return
	 */
	public String getItemParamItemById(Long itemId);
}
