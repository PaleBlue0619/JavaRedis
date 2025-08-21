package com.maxim;

import redis.clients.jedis.Jedis;
public class RedisQuery {
    public static void set(String key, String value) {
        Jedis jedis = JedisDBPool.getConnectJedis();
        jedis.set(key, value);
        jedis.close();
    }
    public static String get(String key) {
        Jedis jedis = JedisDBPool.getConnectJedis();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }
    public static void delete(String key) {
        Jedis jedis = JedisDBPool.getConnectJedis();
        jedis.del(key);
        jedis.close();
    }
    public static boolean exists(String key) {
        Jedis jedis = JedisDBPool.getConnectJedis();
        boolean exists = jedis.exists(key);
        jedis.close();
        return exists;
    }
}