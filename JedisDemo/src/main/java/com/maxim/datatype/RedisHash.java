package com.maxim.datatype;
import com.maxim.JedisDBPool;
import com.maxim.RedisQuery;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.IntStream;

public class RedisHash {
    /*
    Redis Hash
    类似于Java HashMap
    */
    public static void main(String[] args) {
        Jedis jedis = JedisDBPool.getConnectJedis();
        Random rand = new Random(42);
        if (jedis.exists("DemoHash")) {
            jedis.del("DemoHash");
        }

        // 添加元素(三种方式)
        // 方式1: 直接添加单个key
        jedis.hset("DemoHash", "field0", "0");
        jedis.hsetnx("DemoHash", "field1", "1");  // hsetnx只能添加不存在的key, 同时只能添加一个field

        // 方式2: 通过Map批量添加单个key
        Map<String, String> hashMap = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            hashMap.put("field" + String.valueOf(i), String.valueOf(rand.nextGaussian()));
        }
        jedis.hset("DemoHash", hashMap);

        // 方式3: 通过hmset批量添加多个key[与方式二一样]
        jedis.hmset("DemoHash", hashMap);

        // 获取元素
        // 2. 获取一个key中field的value值
        String value0 = jedis.hget("DemoHash", "field0");
        Collection<String> fieldList0 = jedis.hmget("DemoHash", "field0", "field1", "field2");
        System.out.println(value0);
        System.out.println(fieldList0.toString());

        // 1. 获取一个key中的所有field和value
        Collection<String> fieldList = jedis.hkeys("DemoHash");
        Collection<String> valueList = jedis.hvals("DemoHash");
        Map<String, String> resMap = jedis.hgetAll("DemoHash");
        System.out.println(fieldList.toString());
        System.out.println(valueList.toString());
        System.out.println(resMap.toString());

    }
}