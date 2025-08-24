package com.maxim.springdataredisdemo;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.maxim.springdataredisdemo.pojo.User;
import com.maxim.springdataredisdemo.utils.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class HashTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void testHash() {
        Random rand = new Random(42);
        Object del_res, set_res, get_res;

        // Redis Hash Test
        if (redisTemplate.hasKey("DemoHash")){
            redisTemplate.delete("DemoHash");
        }
        if (redisTemplate.hasKey("DemoObject")){
            redisTemplate.delete("DemoObject");
        }

        // Delete Method
        redisTemplate.opsForHash().put("DemoHash", "HashKey1", "Value1");
        del_res = redisTemplate.opsForHash().delete("DemoHash", "HashKey1");

        // Put Method
        redisTemplate.opsForHash().put("DemoHash", "HashKey1", "Value1");         // 方式1: 添加一个元素
        redisTemplate.opsForHash().putIfAbsent("DemoHash", "HashKey2", "Value2");
        redisTemplate.opsForHash().put("DemoObject", "HashKey1", new User("Maxim", rand.nextInt(100))); // 方式2：添加一个对象元素


        Map<String, String> add_map = new HashMap<>();         // 方式3：批量添加元素
        for (int i=0, j=0; i<10; i++, j++){
            add_map.putIfAbsent("HashKey"+j, "Value"+j);
        }
        redisTemplate.opsForHash().putAll("DemoHash", add_map);
        Map<String, User> obj_map = new HashMap<>();
        for (int i=0, j=0; i<10; i++, j++){
            obj_map.putIfAbsent("HashKey"+j, new User("Maxim"+j, rand.nextInt(100)));
        }
        redisTemplate.opsForHash().putAll("DemoObject", obj_map); // 方式4: 批量添加对象

        // Get Method
        get_res = redisTemplate.opsForHash().get("DemoHash", "HashKey1"); // 获取指定键的值
        get_res = redisTemplate.opsForHash().entries("DemoHash");  // 获取所有键值对
        get_res = redisTemplate.opsForHash().keys("DemoHash");
        get_res = redisTemplate.opsForHash().values("DemoHash");
        get_res = redisTemplate.opsForHash().size("DemoHash");
        get_res = redisTemplate.opsForHash().hasKey("DemoHash", "HashKey1");

        User obj_res = (User) redisTemplate.opsForHash().get("DemoObject", "HashKey1");
        System.out.println(obj_res.getAge());
    }

}
