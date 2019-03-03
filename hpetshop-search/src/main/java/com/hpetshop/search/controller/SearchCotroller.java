package com.hpetshop.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hpetshop.search.dto.SearchResult;
import com.hpetshop.search.service.SearchService;
import com.hpetshop.utils.ExceptionUtil;
import com.hpetshop.utils.Result;
import com.hpetshop.utils.StringUtils;

/**
 * 
 * <ul>
 * <li>文件名称: SearchCotroller</li>
 * <li>文件描述:用于查询商品</li>
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
public class SearchCotroller {
	@Autowired
	private SearchService searchService;
	/**
	 * 用于搜索
	 *
	 * @param page 当前页数
	 * @param rows 每页总条数
	 * @return
	 */
	@RequestMapping(value = "query", method = RequestMethod.GET)
	@ResponseBody
	public Result search(@RequestParam("q") String queryString, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer rows) {
		SearchResult search = null;
		if (StringUtils.isEmpty(queryString)) {
			return Result.error(500, "查询条件不能为空");
		} else {
			try {
				// 处理url get传值乱码
				queryString = new String(queryString.getBytes("iso8859-1"), "UTF-8");
				search = searchService.search(queryString, page, rows);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Result.error(500, ExceptionUtil.getStackTrace(e));
			}

		}
		return Result.ok(search);
	}
}
