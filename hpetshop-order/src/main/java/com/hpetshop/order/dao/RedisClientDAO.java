package com.hpetshop.order.dao;

/**
 * <ul>
 * <li>文件名称: RedisClusterDAO</li>
 * <li>文件描述: 用于定义集群版redis的基本操作</li>
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
public interface RedisClientDAO {
    /**
     * 用做读取String类型
     */
    public String get(String key);

    /**
     * 用于写入String类型
     */
    public String set(String key, String value);

    /**
     * 用于读取Hash类型
     */
    public String hget(String key, String field);

    /**
     * 用于写入Hash类型
     */
    public Long hset(String key, String field, String value);

    /**
     * 用于字段值的自增
     */
    public long incr(String key);

    /**
     * 用于做缓存设置有效时间
     */
    public long expire(String key, long seconds);

    /**
     * 用于检查字段是否存在
     */
    public long ttl(String key);

    /**
     * 用于在后台修改时删除这个key
     */
    public long del(String key);

    /**
     * 用于在后台修改时删除这个key
     */
    public long hdel(String hKey, String hField);
}
