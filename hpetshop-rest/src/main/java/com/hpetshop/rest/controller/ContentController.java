package com.hpetshop.rest.controller;

import com.hpetshop.pojo.HpContent;
import com.hpetshop.rest.service.ContentService;
import com.hpetshop.utils.ExceptionUtil;
import com.hpetshop.utils.Result;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <ul>
 * <li>文件名称: ContentController</li>
 * <li>文件描述:用于Content进行管理</li>
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
    private static Logger log = Logger.getLogger(ContentController.class);
    @Autowired
    ContentService contentService;

    /**
     * 用于利用categroyId查询分类内容
     */
    @RequestMapping("content/list/{categoryId}")
    @ResponseBody
    public Result getContent(@PathVariable Long categoryId) {
        try {
            log.info("获取到的categoryId" + categoryId);
            List<HpContent> hpContents = contentService.getContent(categoryId);
            log.info("发送表现层的list" + hpContents);
            return Result.ok(hpContents);
        } catch (Exception e) {
            e.printStackTrace();
            // 将错误信息在前台进行显示
            return Result.error(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
