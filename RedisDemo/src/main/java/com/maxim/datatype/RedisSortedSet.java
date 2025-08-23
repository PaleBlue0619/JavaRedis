package com.maxim.datatype;
import com.maxim.JedisDBPool;
import com.maxim.RedisQuery;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.stream.IntStream;

public class RedisSortedSet {
	// Redis SortedSet 是一个有序集合，集合中的元素是唯一的，并且按照 score 的大小进行排序
    // Redis SortedSet 和Java TreeSet 很相似, 但其底层是SkipList，而TreeSet是红黑树
    public static void main(String[] args){
        Jedis jedis = JedisDBPool.getConnectJedis();
        Random rand = new Random(42);
        if (jedis.exists("DemoSortedSet")) {
            jedis.del("DemoSortedSet");
        }

        // 添加元素(两种方式)
        // 方式1: 直接添加
        jedis.zadd("DemoSortedSet", rand.nextDouble(), "0");

        // 方式2：通过Map批量添加
        Map<String, Double> ScoreMap = new HashMap<>();
        for (int i=0; i<1000; i++){
            ScoreMap.put(String.valueOf(i), rand.nextGaussian());
        }
        jedis.zadd("DemoSortedSet",ScoreMap); // 添加元素

        // 删除元素
        Long remove_res = jedis.zrem("DemoSortedSet", "0"); // 删除元素
        System.out.println(remove_res);

        // 获取元素
        System.out.println(String.valueOf(jedis.zrange("DemoSortedSet", 0, -1)));
        System.out.println(jedis.zcard("DemoSortedSet"));

        // 排序统计
        Long zrank = jedis.zrank("DemoSortedSet", "22");
        System.out.println(zrank);// 获取指定元素的score对应的rank，如果找不到指定的元素则返回null

        Double specified_score = jedis.zscore("DemoSortedSet", "0");
        System.out.println(specified_score);  // 获取指定元素的score，如果找不到指定的元素则返回null
        Long z_count = jedis.zcount("DemoSortedSet", -1, 1);
        System.out.println(z_count);  // 获取score位于指定区间的元素个数
        Collection<String> z_rangeByIndex = jedis.zrange("DemoSortedSet", 0, 10); // 获取索引位于指定区间的元素（也就是rank∈[start,top]的元素, 注意:左闭右闭）
        System.out.println(z_rangeByIndex.toArray().length);
        System.out.println(z_rangeByIndex.toString());

        Collection<String> z_rangeByScore = jedis.zrangeByScore("DemoSortedSet", -.5, .5);
        System.out.println(z_rangeByScore.toArray().length); // 获取score位于指定区间的所有元素
        System.out.println(z_rangeByScore.toString());
    }
}