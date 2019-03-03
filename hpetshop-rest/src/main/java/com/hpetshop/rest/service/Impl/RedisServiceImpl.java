package com.hpetshop.rest.service.Impl;

import com.hpetshop.rest.dao.RedisClientDAO;
import com.hpetshop.rest.service.RedisService;
import com.hpetshop.utils.ExceptionUtil;
import com.hpetshop.utils.Result;
import com.hpetshop.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <ul>
 * <li>文件名称: RedisServiceImpl</li>
 * <li>文件描述:用于提供redis相关服务</li>
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
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisClientDAO redisClientDao;
    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;// 首页内容的Key
    @Value("${INDEX_ITEMCAT_REDIS_KEY}")
    private String INDEX_ITEMCAT_REDIS_KEY;// 首页类目的Key
    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    /**
     * 用于提供服务供manager调用同步redis缓存
     *
     * @param HpId 表中添加到redisField字段
     */
    @Override
    public Result hDelRedisContent(Long HpId) {
        try {
            redisClientDao.hdel(INDEX_CONTENT_REDIS_KEY, String.valueOf(HpId));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return Result.error(500, ExceptionUtil.getStackTrace(e));
        }
        return Result.ok();
    }

    /**
     * 用于提供服务供manager调用同步redis缓存
     */
    @Override
    public Result hDelRedisItemCat(Long parentId) {
        try {
            redisClientDao.hdel(INDEX_ITEMCAT_REDIS_KEY, String.valueOf(parentId));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return Result.error(500, ExceptionUtil.getStackTrace(e));
        }
        return Result.ok();
    }

    /**
     * 用于同步商品详情页内容
     */
    @Override
    public Result DelRedisItem(Long itemKey) {
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        // 拼装redis命名规则:和数据库映射,表名:主键:用处:值
        sb.append(REDIS_ITEM_KEY).append(":").append(itemKey).append(":desc");//删除商品详情
        sb1.append(REDIS_ITEM_KEY).append(":").append(itemKey).append(":base");//删除商品
        try {
            String json = redisClientDao.get(sb1.toString());
            if (StringUtils.isNotEmpty(json)) {
                redisClientDao.del(sb1.toString());
                redisClientDao.del(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok();
    }
}
