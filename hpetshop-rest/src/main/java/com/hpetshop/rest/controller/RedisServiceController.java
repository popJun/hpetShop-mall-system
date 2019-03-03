package com.hpetshop.rest.controller;

import com.hpetshop.rest.service.RedisService;
import com.hpetshop.utils.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <ul>
 * <li>文件名称: RedisServiceController</li>
 * <li>文件描述:用于提供redis相关服务供redis层调用</li>
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
@RequestMapping("redis/cache/sync")
public class RedisServiceController {

    @Autowired
    RedisService redisService;

    /**
     * 用于提供服务供manager调用同步redis缓存
     *
     * @param contentId 表中添加到redisField字段
     */
    @RequestMapping("/content/{contentId}")
    @ResponseBody
    public Result hDelRedisContent(@PathVariable Long contentId) {
        Result result = redisService.hDelRedisContent(contentId);
        return result;
    }

    /**
     * 用于提供服务供manager调用同步redis缓存
     */
    @RequestMapping("/itemCat/{parentId}")
    @ResponseBody
    public Result hDelRedisItemCat(@PathVariable Long parentId) {
        Result result = redisService.hDelRedisItemCat(parentId);
        return result;
    }

    /**
     * 用于提供服务供manager调用同步redis缓存
     */
    @RequestMapping("/item/{itemKey}")
    @ResponseBody
    public Result DelRedisItem(@PathVariable Long itemKey) {
        Result result = redisService.DelRedisItem(itemKey);
        return result;
    }
}
