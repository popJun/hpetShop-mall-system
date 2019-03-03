package com.hpetshop.service;

import com.hpetshop.pojo.HpItemParam;
import com.hpetshop.utils.Result;

/**
 * 
 * <ul>
 * <li>文件名称: ItemParamService</li>
 * <li>文件描述:用作处理Hp_Item_Param表</li>
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
public interface ItemParamService {
	/**
	 * 通过商品类别查找是否已经建立商品规格
	 * 
	 * @param ItemCatId
	 * @return
	 */
	public Result findItemParamByItemCatId(Long ItemCatId);

	/**
	 * 用于保存HpItemParam
	 * 
	 * @param hpItemParam
	 * @return
	 */
	public Result insertItemParam(HpItemParam hpItemParam);
}
