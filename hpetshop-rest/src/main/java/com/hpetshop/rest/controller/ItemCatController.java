package com.hpetshop.rest.controller;

import com.hpetshop.rest.dto.CatResultDTO;
import com.hpetshop.rest.service.ItemCatService;
import com.hpetshop.utils.JsonUtils;
import com.hpetshop.utils.Result;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <ul>
 * <li>文件名称: ItemCatController</li>
 * <li>文件描述:用于前台分类制作</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月6日</li>
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
public class ItemCatController {
    private static Logger log = Logger.getLogger(ItemCatController.class);
    @Autowired
    ItemCatService itemCatService;

    @RequestMapping(value = "itemcat/all", produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String getCatList(String callback) {
        CatResultDTO catResultDTO = itemCatService.getCatList();
        String json = JsonUtils.objectToJson(catResultDTO);
        String resultJson = callback + "(" + json + ")";
        log.info("返回后的jsonp字符串为" + resultJson);
        return resultJson;
    }

    /**
     * 获得该分类下所有商品列表
     */
    @RequestMapping("itemList/{id}")
    @ResponseBody
    public Result getCatItemList(@PathVariable Long id) {
        Result result = itemCatService.getCatItemList1(id);
        return result;
    }
}
