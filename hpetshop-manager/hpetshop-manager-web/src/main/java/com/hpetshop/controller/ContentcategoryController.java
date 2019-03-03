package com.hpetshop.controller;

import com.hpetshop.dto.CMSTreeNodeDTO;
import com.hpetshop.service.ContentcategoryService;
import com.hpetshop.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <ul>
 * <li>文件名称: ContentcategoryController</li>
 * <li>文件描述:easyui前台显示</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>公 司: 厦门市中软件科技有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月7日</li>
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
public class ContentcategoryController {

    @Autowired
    ContentcategoryService contentcategoryService;

    /**
     * 用于获取功能内容分类管理的类目
     */
    @RequestMapping("content/category/list")
    @ResponseBody
    public List<CMSTreeNodeDTO> getContentcategroy(@RequestParam(value = "id", defaultValue = "0") Long parentid) {
        List<CMSTreeNodeDTO> cmsTreeNodeDTOs = contentcategoryService.getContentcategroy(parentid);
        return cmsTreeNodeDTOs;
    }

    /**
     * 在异步树中建立一个新的节点
     */
    @RequestMapping("content/category/create")
    @ResponseBody
    public Result insertContentcategroy(Long parentId, String name) {
        Result result = contentcategoryService.insertContentcategroy(parentId, name);
        return result;
    }

    /**
     * 重命名异步树的类目名称
     */
    @RequestMapping("content/category/update")
    @ResponseBody
    public Result updateContentcategroyName(Long id, String name) {
        Result result = contentcategoryService.updateContentcategroyName(id, name);
        return result;
    }

    /**
     * 用于异步树删除类目
     *
     * @param id requestParam 在没有传值时默认为0
     */
    @RequestMapping("content/category/delete")
    @ResponseBody
    public Result deleteContentcategroy(long id) {
        Result result = contentcategoryService.deleteContentcategroy(id);
        return result;
    }
}
