package com.maxim;
import redis.clients.jedis.Jedis;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Main {
    public static void main(String[] args)
    {
        Jedis jedis = JedisDBPool.getConnectJedis();
        RedisQuery.set("name", "Maxim");
        System.out.println(RedisQuery.get("name"));
        RedisQuery.delete("name");
        System.out.println(RedisQuery.exists("name"));
    }
}