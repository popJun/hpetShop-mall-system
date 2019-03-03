package com.hpetshop.order.dao.impl;

import com.hpetshop.order.dao.RedisClientDAO;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import redis.clients.jedis.JedisCluster;

/**
 * <ul>
 * <li>文件名称: RedisClusterDAOImpl</li>
 * <li>文件描述:用于定义集群版redis的基本操作</li>
 * <li>版权所有: 版权所有(C) 2018</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月25日</li>
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
public class RedisClusterDAOImpl implements RedisClientDAO {
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 用做读取String类型
     */
    @Override
    public String get(String key) {
        String string = jedisCluster.get(key);
        jedisClose(jedisCluster);
        return string;
    }

    /**
     * 用于写入String类型
     */
    @Override
    public String set(String key, String value) {
        String set = jedisCluster.set(key, value);
        jedisClose(jedisCluster);
        return set;
    }

    /**
     * 用于读取Hash类型
     */
    @Override
    public String hget(String key, String field) {
        String hget = jedisCluster.hget(key, field);
        jedisClose(jedisCluster);
        return hget;
    }

    /**
     * 用于写入Hash类型
     */
    @Override
    public Long hset(String key, String field, String value) {
        Long hset = jedisCluster.hset(key, field, value);
        jedisClose(jedisCluster);
        return hset;
    }

    /**
     * 用于字段值的自增
     */
    @Override
    public long incr(String key) {
        Long incr = jedisCluster.incr(key);
        jedisClose(jedisCluster);
        return incr;
    }

    /**
     * 用于做缓存设置有效时间
     */
    @Override
    public long expire(String key, long seconds) {
        Long expire = jedisCluster.expire(key, (int) seconds);
        jedisClose(jedisCluster);
        return expire;
    }

    /**
     * 用于检查字段是否存在
     */
    @Override
    public long ttl(String key) {
        Long ttl = jedisCluster.ttl(key);
        jedisClose(jedisCluster);
        return ttl;
    }

    /**
     * 用于在key修改后删除key
     */
    @Override
    public long del(String key) {
        Long del = jedisCluster.del(key);
        jedisClose(jedisCluster);
        return del;
    }

    /**
     * 用于在key修改后删除key
     */
    @Override
    public long hdel(String hKey, String hField) {
        Long hdel = jedisCluster.hdel(hKey, hField);
        jedisClose(jedisCluster);
        return hdel;
    }

    /**
     * 关闭jedis
     */
    private void jedisClose(JedisCluster jedisCluster) {
        try {
            jedisCluster.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
