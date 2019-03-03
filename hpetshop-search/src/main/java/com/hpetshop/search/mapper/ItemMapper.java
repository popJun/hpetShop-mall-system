package com.hpetshop.search.mapper;

import java.util.List;

import com.hpetshop.search.dto.ItemDTO;

/**
 * 
 * <ul>
 * <li>文件名称: ItemMapper</li>
 * <li>文件描述:用于建立索引库查找数据库</li>
 * <li>版权所有: 版权所有(C) 2018</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月29日</li>
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
public interface ItemMapper {
	/**
	 * 用于查找建立索引库所必须字段
	 * 
	 * @return
	 */
	public List<ItemDTO> getItem();
	/**
	 * 通过id查找
	 * 
	 * @param itemId
	 * @return
	 */
	public ItemDTO getItemById(Long itemId);
}
