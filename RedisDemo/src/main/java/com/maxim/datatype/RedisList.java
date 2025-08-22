package com.maxim.datatype;
import com.maxim.JedisDBPool;
import com.maxim.RedisQuery;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public class RedisList {
    /*
    Redis List:
    Redis List本质既不是单纯的Stack(LIFO) & Queue(FIFO)，而是一个双端队列(Deque)
    什么是双端队列? 本质就是支持从两端进行插入和删除元素的数据结构
    -lpush：从left侧插入
    -rpush: 从right侧插入
    -lpop: 从left侧弹出
    -rpop: 从right侧弹出
    */
    public static void main(String[] args)
    {
        // 方式1
        Jedis jedis = JedisDBPool.getConnectJedis();  // 创建Jedis连接池
        if (jedis.exists("DemoList")){
            jedis.del("DemoList");
        }
        jedis.lpush("DemoList", "0"); // 从左侧插入
        jedis.rpush("DemoList","1");  // 从右侧插入
        jedis.lpop("DemoList");  // 从左侧弹出"0"

        // 创建数组的几种方式
        // 原生数组方法
        int[] data_list = new int[]{2, 3, 4, 5};

        // List.of & Arrays.asList()
        // Collection<Integer> data_list = List.of(2, 3, 4, 5);

        // Stream.range (Stream API)
        // int[] data_list = IntStream.range(2, 5+1).toArray();

        for (Integer i : data_list){
            jedis.lpush("DemoList", i.toString());
        }
        Collection<String> demo_list = jedis.lrange("DemoList", 0, 5);
        System.out.println(demo_list.toString());

    }
}