package com.maxim.datatype;
import com.maxim.JedisDBPool;
import com.maxim.RedisQuery;
import redis.clients.jedis.Jedis;

public class RedisString {
    public static void main(String[] args)
    {
        // 方式1
        Jedis jedis = JedisDBPool.getConnectJedis();  // 创建Jedis连接池
        if (jedis.exists("name")){
            jedis.del("name");
        }

        jedis.set("name", "Maxim");
        System.out.println(jedis.get("name"));

        // 方式2
        RedisQuery.set("name", "Maxim");
        System.out.println(RedisQuery.get("name"));
        RedisQuery.delete("name");
        System.out.println(RedisQuery.exists("name"));
    }
}