package com.maxim.datatype;
import com.maxim.JedisDBPool;
import com.maxim.RedisQuery;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

public class RedisKey {
    public static void main(String[] args)
    {
        // 方式1
        Jedis jedis = JedisDBPool.getConnectJedis();  // 创建Jedis连接池
        // 获取数据并输出
        Set<String> keys = jedis.keys("*");
        Iterator<String> it=keys.iterator() ;
        while(it.hasNext()){
            String key = it.next();
            System.out.println(key);
        }

    }
}