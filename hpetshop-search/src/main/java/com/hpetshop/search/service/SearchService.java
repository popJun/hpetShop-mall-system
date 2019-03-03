package com.hpetshop.search.service;

import com.hpetshop.search.dto.SearchResult;

public interface SearchService {
	/**
	 * 用于搜索
	 * 
	 * @param queryCondition查询条件
	 * @param page当前页数
	 * @param rows每页总条数
	 * @return
	 */
	public SearchResult search(String queryString, int page, int rows);
}
