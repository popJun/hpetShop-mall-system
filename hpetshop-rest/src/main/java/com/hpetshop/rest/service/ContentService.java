package com.hpetshop.rest.service;

import java.util.List;

import com.hpetshop.pojo.HpContent;

/**
 * 
 * <ul>
 * <li>文件名称: ContentService</li>
 * <li>文件描述:用于对分类内容进行处理</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月15日</li>
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
public interface ContentService {
	/**
	 * 
	 * @param categoryId
	 * @return
	 */
	public List<HpContent> getContent(Long categoryId);
}
