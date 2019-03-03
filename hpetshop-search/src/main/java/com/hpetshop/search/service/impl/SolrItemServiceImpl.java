package com.hpetshop.search.service.impl;

import java.io.IOException;
import java.util.List;

import com.hpetshop.mapper.HpItemCatMapper;
import com.hpetshop.mapper.HpItemDescMapper;
import com.hpetshop.pojo.HpItem;
import com.hpetshop.pojo.HpItemCat;
import com.hpetshop.pojo.HpItemDesc;
import com.hpetshop.utils.JsonUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hpetshop.search.dto.ItemDTO;
import com.hpetshop.search.mapper.ItemMapper;
import com.hpetshop.search.service.SolrItemService;
import com.hpetshop.utils.ExceptionUtil;
import com.hpetshop.utils.Result;
/**
 * 
 * <ul>
 * <li>文件名称: ItemServiceImpl</li>
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
@Service
public class SolrItemServiceImpl implements SolrItemService {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private HpItemDescMapper hpItemDescMapper;
	@Autowired
	private HpItemCatMapper hpItemCatMapper;
	@Autowired
	private SolrServer solrServer;
	/**
	 * 导入商品信息到索引库
	 * 
	 * @return
	 */
	@Override
	public Result importItemToSolr() {
		// 查询商品信息
		List<ItemDTO> list = itemMapper.getItem();
		// 把商品信息加入索引库
		for (ItemDTO item : list) {
			// 创建一个SolrInputDocument对象
			SolrInputDocument document = new SolrInputDocument();
			document.setField("id", item.getId());
			document.setField("item_title", item.getTitle());
			document.setField("item_sell_point", item.getSell_point());
			document.setField("item_price", item.getPrice());
			document.setField("item_image", item.getImage());
			document.setField("item_category_name", item.getCategory_name());
			document.setField("item_desc", item.getItem_des());
			// 写入索引库
			try {
				solrServer.add(document);
				// 提交修改--这不决定不能少否则没用
				solrServer.commit();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Result.error(500, ExceptionUtil.getStackTrace(e));

			}
		}
		return Result.ok();
	}
	/**
	 * 用于删除索引库中所有数据
	 * 
	 * @return
	 */
	@Override
	public Result delItemToSolr() {
		try {
			solrServer.deleteByQuery("*:*");
			solrServer.commit();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Result.ok();
	}
	/**
	 * 同步删除

	 * @param itemId
	 * @return
	 */
	@Override
	public Result delItemToSolrById(Long itemId) {
		try {
			solrServer.deleteById(String.valueOf(itemId));
			solrServer.commit();//需要这家否则无用
			return Result.ok();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return Result.error(500, ExceptionUtil.getStackTrace(e));
		}

	}
	/**
	 * 用于同步数据库
	 * 
	 * @param hpItemJson
	 * @return
	 */
	@Override
	public Result uploadOrSaveItemToSolrById(String hpItemJson) {
		HpItem item = JsonUtils.jsonToPojo(hpItemJson, HpItem.class);
		// 创建一个SolrInputDocument对象
		SolrInputDocument document = new SolrInputDocument();
		document.setField("id", item.getId());
		document.setField("item_title", item.getTitle());
		document.setField("item_sell_point", item.getSellPoint());
		document.setField("item_price", item.getPrice());
		document.setField("item_image", item.getImage());
		document.setField("item_category_name", getCategoryName(item.getCid()));
		document.setField("item_desc", getItemDes(item.getId()));
		// 写入索引库
		try {
			solrServer.add(document);
			// 提交修改--这不决定不能少否则没用
			solrServer.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Result.error(500, ExceptionUtil.getStackTrace(e));

		}
		return Result.ok();

	}
/**  
 *    
 *   用于获得商品描述
 * @author wushaochuan 
 * @date 2018/4/13 10:49
 * @param hpItemId 商品id
 * @return java.lang.String  
 */  
	private String getItemDes(Long hpItemId){
		HpItemDesc hpItemDesc = hpItemDescMapper.selectByPrimaryKey(hpItemId);
	    return  hpItemDesc.getItemDesc();
	}
	/**
	 *
	 *用于获取商品类别名
	 * @author wushaochuan
	 * @date 2018/4/13 11:00
	 * @param cid 所属类目ld
	 * @return java.lang.String
	 */
	private  String getCategoryName(Long cid){
		HpItemCat hpItemCat = hpItemCatMapper.selectByPrimaryKey(cid);
	    return hpItemCat.getName();
	}
}
