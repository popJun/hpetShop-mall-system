package com.hpetshop.service;

import com.hpetshop.dto.AllPageDTO;
import com.hpetshop.pojo.HpContent;
import com.hpetshop.utils.Result;

/**
 * 
 * <ul>
 * <li>文件名称: ContentService</li>
 * <li>文件描述:用于对首页分栏目进行内容管理</li>
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
	 * 通过内容类目的id查询内容
	 * 
	 * @param page
	 *            当前页数
	 * @param row
	 *            每页条数
	 * @param categoryId
	 *            类目id
	 * @return
	 */
	public AllPageDTO getContent(int page, int row, Long categoryId);
	/**
	 * 用于保存内容
	 * 
	 * @param hpContent
	 * @return
	 */
	public Result saveContent(HpContent hpContent);
	/**
	 * 用于删除内容
	 * 
	 * @param id删除内容的id
	 * @return
	 */
	public Result deleteContent(Long id);
	/**
	 * 用于更新内容
	 * 
	 * @param hpContent
	 * @return
	 */
	public Result updateContent(HpContent hpContent);
}
