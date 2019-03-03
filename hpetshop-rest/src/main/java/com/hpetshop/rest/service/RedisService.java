package com.hpetshop.rest.service;

import com.hpetshop.utils.Result;

/**
 * <ul>
 * <li>文件名称: RedisService</li>
 * <li>文件描述: 提供redis相关服务</li>
 * <li>版权所有: 版权所有(C) 2018</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月28日</li>
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
public interface RedisService {
    /**
     * 用于提供服务供manager调用同步redis缓存
     *
     * @param contentId 表中添加到redisField字段
     */
    Result hDelRedisContent(Long contentId);

    /**
     * 用于提供服务供manager调用同步redis缓存
     */
    Result hDelRedisItemCat(Long parentId);

    /**
     * 提供用于同步redis商品详情页缓存
     */
    Result DelRedisItem(Long itemKey);

}
