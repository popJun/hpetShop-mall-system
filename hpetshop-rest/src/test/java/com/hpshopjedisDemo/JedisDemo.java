package com.hpshopjedisDemo;

import org.testng.annotations.Test;

import java.util.HashSet;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <ul>
 * <li>文件名称: JedisDemo</li>
 * <li>文件描述:对redis的一些</li>
 * <li>版权所有: 版权所有(C) 2016</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018年3月18日</li>
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
public class JedisDemo {
    @Test
    public void jedisSigle() {// redis单机版测试
        Jedis jedis = new Jedis("192.168.138.128", 6379);
        jedis.set("name", "吴少川");
        System.out.println(jedis.get("name"));
        jedis.close();
    }

    @Test
    public void redisPool() {// redis连接池
        JedisPool jedisPool = new JedisPool("192.168.138.128", 6379);
        Jedis jedis = jedisPool.getResource();
        // jedis.selece(0);//选择redis的数据库
        jedis.set("name", "吴少川");
        System.out.println(jedis.get("name"));
        jedis.close();
    }

    @Test
    public void redisCluster() {// redis集群版测试
        JedisPoolConfig config = new JedisPoolConfig();
        config = new JedisPoolConfig();
        config.setMaxTotal(60000);// 设置最大连接数
        config.setMaxIdle(1000); // 设置最大空闲数
        config.setMaxWaitMillis(3000);// 设置超时时间
        config.setTestOnBorrow(true);
        HashSet<HostAndPort> jedisSet = new HashSet<HostAndPort>();
        jedisSet.add(new HostAndPort("192.168.138.128", 7001));
        jedisSet.add(new HostAndPort("192.168.138.128", 7002));
        jedisSet.add(new HostAndPort("192.168.138.128", 7003));
        jedisSet.add(new HostAndPort("192.168.138.128", 7004));
        jedisSet.add(new HostAndPort("192.168.138.128", 7005));
        jedisSet.add(new HostAndPort("192.168.138.128", 7006));
        JedisCluster jedisCluster;
        try {
            jedisCluster = new JedisCluster(jedisSet, config);
            jedisCluster.set("key", "123456");
            System.out.println(jedisCluster.get("key"));
            jedisCluster.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
