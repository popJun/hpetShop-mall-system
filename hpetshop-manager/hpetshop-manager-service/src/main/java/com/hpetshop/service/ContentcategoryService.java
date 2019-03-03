package com.hpetshop.service;

import java.util.List;

import com.hpetshop.dto.CMSTreeNodeDTO;
import com.hpetshop.utils.Result;

/**
 * 
 * <ul>
 * <li>文件名称: ContentcategoryService</li>
 * <li>文件描述:对于cms系统首页的栏目异步树管理</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月7日</li>
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
public interface ContentcategoryService {
	/**
	 * 获取cms系统中的异步树栏目
	 * 
	 * @param parentid
	 * @return
	 */
	public List<CMSTreeNodeDTO> getContentcategroy(Long parentid);

	/**
	 * 新增cms系统中异步树的栏目
	 * 
	 * @param parentId
	 * @param name
	 * @return
	 */
	public Result insertContentcategroy(long parentId, String name);
	/**
	 * 重命名异步树的类目名称
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	public Result updateContentcategroyName(long id, String name);
	/**
	 * 删除异步树的类目
	 * 
	 * @param parentId
	 * @param id
	 * @return
	 */
	public Result deleteContentcategroy(long id);
}
