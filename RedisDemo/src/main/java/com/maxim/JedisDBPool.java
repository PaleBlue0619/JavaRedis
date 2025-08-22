package com.maxim;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

// Jedis 工具类
public class JedisDBPool {
    public static JedisPool jedisPool;
    static {
        try {
            Properties properties = new Properties();
            InputStream in = JedisDBPool.class.getClassLoader().getResourceAsStream("redis.properties");
            properties.load(in);
            JedisPoolConfig config = new JedisPoolConfig();

            // 设置Redis配置参数(host/port等参数->redis.properties->properties)
            config.setMaxTotal(Integer.parseInt(properties.getProperty("maxTotal")));
            config.setMaxIdle(Integer.parseInt(properties.getProperty("maxIdle")));
            config.setMinIdle(Integer.parseInt(properties.getProperty("minIdle")));
            config.setMaxWaitMillis(Long.parseLong(properties.getProperty("maxWaitMillis")));
            config.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty("testOnBorrow")));
            config.setTestOnReturn(Boolean.parseBoolean(properties.getProperty("testOnReturn")));
            config.setTimeBetweenEvictionRunsMillis(Long.parseLong(properties.getProperty("timeBetweenEvictionRunsMillis")));
            config.setMinEvictableIdleTimeMillis(Long.parseLong(properties.getProperty("minEvictableIdleTimeMillis")));
            config.setNumTestsPerEvictionRun(Integer.parseInt(properties.getProperty("numTestsPerEvictionRun")));

            // 创建JedisPool
            jedisPool = new JedisPool(config, properties.getProperty("host"),
                    Integer.parseInt(properties.getProperty("port"))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Jedis getConnectJedis() {
        return jedisPool.getResource();
    }
}
