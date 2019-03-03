package com.hpetshop.search.controller;

import com.hpetshop.pojo.HpItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.hpetshop.search.service.SolrItemService;
import com.hpetshop.utils.Result;

/**
 * 
 * <ul>
 * <li>文件名称: SolrItemController</li>
 * <li>文件描述:用于索引库维护的</li>
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
@Controller
@RequestMapping("solr/manager")
public class SolrItemController {
	@Autowired
	SolrItemService solrItemService;
	/**
	 * 导入商品信息到索引库
	 * 
	 * @return
	 */
	@RequestMapping("/importall")
	@ResponseBody
	public Result importItemToSolr() {
		Result result = solrItemService.importItemToSolr();
		return result;
	}
	/**
	 * 删除索引库数据
	 * 
	 * @return
	 */
	@RequestMapping("/delall")
	@ResponseBody
	public Result delItemToSolr() {
		Result result = solrItemService.delItemToSolr();
		return result;
	}
	/**
	 * 通过itemId来删除索引库
	 * 
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/del/{itemId}")
	@ResponseBody
	public Result delItemToSolrById(@PathVariable long itemId) {
		Result result = solrItemService.delItemToSolrById(itemId);
		return result;

	}
	/**
	 * 更新或者添加索引库 应为为一个事务所以搜到的是以前的
	 * 
	 * @param hpItemJson
	 * @return
	 */
	@RequestMapping(value = "/uploadOrSave", method= RequestMethod.POST, produces="application/json;charset=utf-8;")
	@ResponseBody
	public Result uploadOrSaveItemToSolrById(@RequestBody String hpItemJson) {
		Result result = solrItemService.uploadOrSaveItemToSolrById(hpItemJson);
		return result;

	}
}
