package com.maxim.datatype;
import com.maxim.JedisDBPool;
import com.maxim.RedisQuery;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RedisSet {
    public static void main(String[] args) {
        Jedis jedis = JedisDBPool.getConnectJedis();
        if (jedis.exists("DemoSet")){
            jedis.del("DemoSet");
        }
        jedis.sadd("DemoSet", new String[]{"0","1"}); // 添加元素
        Long res = jedis.srem("DemoSet", "0"); // 移除元素(若不存在也不会报错, 会返回0, 删除成功则返回1)
        jedis.sadd("DemoSet", "1"); // 无需(不按照插入顺序/大小排序)+不重复
;
        // Java中构造集合的多种方式
        // 1.直接使用数组
        // String[] data_set = new String[]{"1", "2", "3", "4", "5"};

        // 2.Set.of
        // Set<String> data_set = Set.of("1", "2", "3", "4", "5");

        // 3.Stream API
        Set<Integer> data_set = IntStream.range(2, 6)
                .boxed()  // 将int转换为Integer
                .collect(Collectors.toSet());
//        Set<String> data_set = IntStream.range(2, 6)
//                .mapToObj(String::valueOf)  // 将数字转换为字符串
//                .collect(Collectors.toSet());

        for (int i=0; i<data_set.size(); i++){
            jedis.sadd("DemoSet", data_set.toArray()[i].toString());
        }
        System.out.println(jedis.smembers("DemoSet"));  // 获取集合全部元素
        System.out.println(jedis.scard("DemoSet"));     // 获取集合元素个数

        // 集合运算
        if (jedis.exists("DemoSet2")){
            jedis.del("DemoSet2");
        }
        jedis.sadd("DemoSet2", new String[]{"1","1","5","6"});
        System.out.println(jedis.sinter("DemoSet", "DemoSet2"));  // intersection交集
        System.out.println(jedis.sunion("DemoSet", "DemoSet2"));  // union并集
        System.out.println(jedis.sdiff("DemoSet", "DemoSet2"));   // differentiation差集

    }
}
