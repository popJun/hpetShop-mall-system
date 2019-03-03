package com.hpetshop.controller;

import com.hpetshop.dto.AllPageDTO;
import com.hpetshop.pojo.HpContent;
import com.hpetshop.service.ContentService;
import com.hpetshop.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <ul>
 * <li>文件名称: ContentController</li>
 * <li>文件描述:对首页栏目分类内容进行管理</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月15日</li>
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
public class ContentController {
    @Autowired
    private ContentService contentService;

    /**
     * 类目的id查询内容
     */
    @RequestMapping("content/query/list")
    @ResponseBody
    public AllPageDTO getContent(Integer page, Integer rows, Long categoryId) {
        AllPageDTO allPageDTO = contentService.getContent(page, rows, categoryId);
        return allPageDTO;
    }

    /**
     * 用于保存内容
     */
    @RequestMapping("content/save")
    @ResponseBody
    public Result saveContent(HpContent hpContent) {
        Result result = contentService.saveContent(hpContent);
        return result;
    }

    /**
     * 用于删除内容
     */
    @RequestMapping("content/delete")
    @ResponseBody
    public Result deleteConent(String ids) {
        String[] idStr = ids.split(",");
        for (String id : idStr) {
            contentService.deleteContent(Long.parseLong(id));
        }
        return Result.ok();
    }

    /**
     * 用于更新内容
     */
    @RequestMapping("rest/content/edit")
    @ResponseBody
    public Result updateContent(HpContent hpContent) {
        Result result = contentService.updateContent(hpContent);
        return result;
    }
}
