package com.hpetshop.search.service;

import com.hpetshop.pojo.HpItem;
import com.hpetshop.utils.Result;

/**
 * 
 * <ul>
 * <li>文件名称: ItemService</li>
 * <li>文件描述:用于创建document导入索引库</li>
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
public interface SolrItemService {
	/**
	 * 导入商品信息到索引库
	 * 
	 * @return
	 */
	public Result importItemToSolr();
	/**
	 * 删除索引库中所有信息
	 * 
	 * @return
	 */
	public Result delItemToSolr();
	/**
	 * 通过id删除索引库
	 * 
	 * @param itemId
	 * @return
	 */
	public Result delItemToSolrById(Long itemId);
	/**
	 * 用于同步数据库
	 * 
	 * @param hpItemJson
	 * @return
	 */
	public Result uploadOrSaveItemToSolrById(String hpItemJson);
}
