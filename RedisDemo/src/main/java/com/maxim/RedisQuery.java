package com.maxim;

import redis.clients.jedis.Jedis;
public class RedisQuery {
    // Jedis 增删改查
    public static void set(String key, String value) {
        Jedis jedis = JedisDBPool.getConnectJedis();
        jedis.set(key, value);
        jedis.close();
    } // 增加&修改键值对
    public static String get(String key) {
        Jedis jedis = JedisDBPool.getConnectJedis();
        String value = jedis.get(key);
        jedis.close();
        return value;
    } // 查询键值对
    public static void delete(String key) {
        Jedis jedis = JedisDBPool.getConnectJedis();
        jedis.del(key);
        jedis.close();
    } // 删除键值对
    public static boolean exists(String key) {
        Jedis jedis = JedisDBPool.getConnectJedis();
        boolean exists = jedis.exists(key);
        jedis.close();
        return exists;
    } // 判断键值对是否存在
}