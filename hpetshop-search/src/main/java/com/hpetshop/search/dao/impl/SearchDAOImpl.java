package com.hpetshop.search.dao.impl;

import com.hpetshop.search.dao.SearchDAO;
import com.hpetshop.search.dto.ItemDTO;
import com.hpetshop.search.dto.SearchResult;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <ul>
 * <li>文件名称: SearchDAOImpl</li>
 * <li>文件描述:用于搜索物品</li>
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
@Repository
public class SearchDAOImpl implements SearchDAO {
    @Autowired
    private SolrServer solrServer;

    /**
     * 根据搜索条件在索引库搜索相关物品
     *
     * @param solrQuery 搜索条件
     */
    @Override
    public SearchResult search(SolrQuery solrQuery) {
        SearchResult searchResult = new SearchResult();// 用于封装结果
        List<ItemDTO> itemDTOList = new ArrayList<ItemDTO>();// 用于存入商品信息
        try {
            QueryResponse query = solrServer.query(solrQuery);
            SolrDocumentList results = query.getResults();
            searchResult.setRecordCount(results.getNumFound());// 总记录数
            // 对标题进行高亮取值
            Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
            for (SolrDocument solrDocument : results) {
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setId((String) solrDocument.get("id"));
                String title = "";
                // 将title进行高亮显示并取出
                List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
                if (list == null) {
                    title = (String) solrDocument.get("item_title");
                } else {
                    title = list.get(0);
                }
                itemDTO.setTitle(title);// 商品title
                itemDTO.setImage((String) solrDocument.get("item_image"));// 商品图片
                itemDTO.setPrice((long) solrDocument.get("item_price"));// 商品价格
                itemDTO.setSell_point((String) solrDocument.get("item_sell_point"));// 商品卖点
                itemDTO.setCategory_name((String) solrDocument.get("item_category_name"));// 商品类目名称
                itemDTOList.add(itemDTO);
            }
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        searchResult.setItemLists(itemDTOList);
        return searchResult;
    }
}
