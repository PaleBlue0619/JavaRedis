package com.maxim.springdataredisdemo;
import java.util.Random;

import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.maxim.springdataredisdemo.pojo.User;
import com.maxim.springdataredisdemo.utils.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class StringTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;  // 注入RedisTemplate

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void testString(){
        Random rand = new Random(42);
        Object pop_res, set_res, get_res;

        // delete Method (getAndDelete[本质上就是pop])
        if (redisTemplate.hasKey("DemoString")){
            redisTemplate.delete("DemoString");
        }
        if (redisTemplate.hasKey("DemoObject")){
            redisTemplate.delete("DemoObject");
        }
        redisTemplate.opsForValue().set("DemoString", "0");
        // Object value1 = redisTemplate.opsForValue().getAndDelete("DemoString");
        // System.out.println(value1);

        // set Method (set/setIfAbsent/setIfPresent/JackSon)
        set_res = redisTemplate.opsForValue().setIfAbsent("DemoString", "1");
        redisTemplate.opsForValue().setIfPresent("DemoString", "2");
        User obj = new User("Maxim",rand.nextInt(100));
        redisUtil.set("DemoObject",obj);  // 这里必须使用重写后的serialization方法
        System.out.println(JSON.toJSONString(obj));

        // get Method (get)
        Object value = redisTemplate.opsForValue().get("DemoString");
        System.out.println(value);
        obj = (User) redisTemplate.opsForValue().get("DemoObject");
        System.out.println(obj.getAge());
    }
}
