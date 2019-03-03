package com.hpetshop.search.service.impl;

import com.hpetshop.search.dao.SearchDAO;
import com.hpetshop.search.dto.SearchResult;
import com.hpetshop.search.service.SearchService;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <ul>
 * <li>文件名称: SearchServiceImpl</li>
 * <li>文件描述:</li>
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
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDAO searchDAO;

    /**
     * 用于搜索
     *
     * @param queryString 查询条件
     * @param page        当前页数
     * @param rows        每页总条数
     */
    @Override
    public SearchResult search(String queryString, int page, int rows) {
        // 用于设置查询条件
        SolrQuery solrQuery = new SolrQuery();
        // 查询条件
        solrQuery.setQuery(queryString);
        // 设置分页
        solrQuery.setStart((page - 1) * rows);
        // 设置每页总数
        solrQuery.setRows(rows);
        // 设置默认搜索域 --df在solr代表默认搜索域
        solrQuery.set("df", "item_keywords");
        // 设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        // 设置高亮样式
        solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
        solrQuery.setHighlightSimplePost("</em>");
        SearchResult search = searchDAO.search(solrQuery);
        // 设置当然页
        search.setCurPage(page);
        // 计算总页数
        long pageCount = search.getPageCount();// 总条数
        long countPage = pageCount / rows;
        if (pageCount % rows > 0) {
            countPage++;
        }
        search.setPageCount(countPage);
        return search;
    }
}
