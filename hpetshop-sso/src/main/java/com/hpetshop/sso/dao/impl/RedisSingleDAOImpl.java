package com.hpetshop.sso.dao.impl;


import com.hpetshop.sso.dao.RedisClientDAO;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * <ul>
 * <li>文件名称: RedisSingleDAOImpl</li>
 * <li>文件描述:用于定义单机版redis的基本操作</li>
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
// 可用注解也可在spring中bean实例
public class RedisSingleDAOImpl implements RedisClientDAO {
	@Autowired
	private JedisPool jedisPool;
	/**
	 * 用做读取String类型
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}
	/**
	 * 用于写入String类型
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String set = jedis.set(key, value);
		jedis.close();
		return set;
	}
	/**
	 * 用于读取Hash类型
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	@Override
	public String hget(String key, String field) {
		Jedis jedis = jedisPool.getResource();
		String hget = jedis.hget(key, field);
		jedis.close();
		return hget;
	}
	/**
	 * 用于写入Hash类型
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	@Override
	public Long hset(String key, String field, String value) {
		Jedis jedis = jedisPool.getResource();
		Long hset = jedis.hset(key, field, value);
		jedis.close();
		return hset;
	}
	/**
	 * 用于字段值的自增
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long incr = jedis.incr(key);
		jedis.close();
		return incr;
	}
	/**
	 * 用于做缓存设置有效时间
	 * 
	 * @param key
	 * @param second
	 * @return
	 */
	@Override
	public long expire(String key, long seconds) {
		Jedis jedis = jedisPool.getResource();
		Long expire = jedis.expire(key, (int) seconds);
		jedis.close();
		return expire;
	}
	/**
	 * 用于检查字段是否存在
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long ttl = jedis.ttl(key);
		jedis.close();
		return ttl;
	}
	/**
	 * 用于在key修改是删除key
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public long del(String key) {
		Jedis jedis = jedisPool.getResource();
		Long del = jedis.del(key);
		jedis.close();
		return del;
	}
	@Override
	public long hdel(String hKey, String hField) {
		Jedis jedis = jedisPool.getResource();
		Long hdel = jedis.hdel(hKey, hField);
		jedis.close();
		return hdel;
	}
}
