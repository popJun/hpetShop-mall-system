package com.hpetshop.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.hpetshop.search.dto.SearchResult;

/**
 * 
 * <ul>
 * <li>文件名称: SearchDAO</li>
 * <li>文件描述:商品搜索dao</li>
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
public interface SearchDAO {
	/**
	 * 根据搜索条件在索引库搜索相关物品
	 * 
	 * @param solrQuery搜索条件
	 * @return
	 */
	public SearchResult search(SolrQuery solrQuery);
}
