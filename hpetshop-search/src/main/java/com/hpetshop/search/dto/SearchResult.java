package com.hpetshop.search.dto;

import java.util.List;

/**
 * 
 * <ul>
 * <li>文件名称: SearchResult</li>
 * <li>文件描述:用于返回solr查找到值</li>
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
public class SearchResult {
	// 查询结果
	private List<ItemDTO> itemLists;
	// 总记录数
	private long recordCount;
	// 总页数
	private long pageCount;
	// 当前页
	private long curPage;
	public List<ItemDTO> getItemLists() {
		return itemLists;
	}
	public void setItemLists(List<ItemDTO> itemLists) {
		this.itemLists = itemLists;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public long getPageCount() {
		return pageCount;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	public long getCurPage() {
		return curPage;
	}
	public void setCurPage(long curPage) {
		this.curPage = curPage;
	}

}
