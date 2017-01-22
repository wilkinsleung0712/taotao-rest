package com.taotao.rest.test.redius;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class RediusTester {

    @Test
    public void testRediusSingleConnection() {
        // 创建一个jedis的对象。
        Jedis jedisClient = new Jedis("127.0.0.1", 6379);
        // 调用jedis对象的方法，方法名称和redis的命令一致。
        jedisClient.set("testValue", "wilkins");
        assertEquals("Excepted equals: ", "wilkins",
                jedisClient.get("testValue"));
        // 关闭jedis。
        jedisClient.close();
    }

    /**
     * 使用连接池
     */
    @Test
    public void testJedisPool() {
        // 创建jedis连接池
        JedisPool pool = new JedisPool("127.0.0.1", 6379);
        // 从连接池中获得Jedis对象
        Jedis jedis = pool.getResource();
        String string = jedis.get("testValue");
        System.out.println(string);
        // 关闭jedis对象
        jedis.close();
        pool.close();
    }

    /**
     * 单机版测试
     * <p>
     * Title: testSpringJedisSingle
     * </p>
     * <p>
     * Description:
     * </p>
     */
    @Test
    public void TestRediusSingle() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "classpath:spring/applicationContext-*.xml");
        JedisPool jedisPoll = appContext.getBean(JedisPool.class);
        // 从连接池中获得Jedis对象
        Jedis jedis = jedisPoll.getResource();
        String string = jedis.get("testValue");
        System.out.println(string);
        // 关闭jedis对象
        jedis.close();
        jedisPoll.close();

    }

    // @Test
    // public void testJedisCluster() {
    // HashSet<HostAndPort> nodes = new HashSet<>();
    // nodes.add(new HostAndPort("192.168.25.153", 7001));
    // nodes.add(new HostAndPort("192.168.25.153", 7002));
    // nodes.add(new HostAndPort("192.168.25.153", 7003));
    // nodes.add(new HostAndPort("192.168.25.153", 7004));
    // nodes.add(new HostAndPort("192.168.25.153", 7005));
    // nodes.add(new HostAndPort("192.168.25.153", 7006));
    //
    // JedisCluster cluster = new JedisCluster(nodes);
    //
    // cluster.set("key1", "1000");
    // String string = cluster.get("key1");
    // System.out.println(string);
    //
    // cluster.close();
    // }

}
