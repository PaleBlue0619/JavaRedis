package com.maxim.springdataredisdemo;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.maxim.springdataredisdemo.pojo.User;
import com.maxim.springdataredisdemo.utils.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class SetTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void testSet(){
        Random rand = new Random(42);
        Object del_res, set_res, get_res;

        if (redisTemplate.hasKey("DemoSet")){
            redisTemplate.delete("DemoSet");
        }
        if (redisTemplate.hasKey("DemoObject")){
            redisTemplate.delete("DemoObject");
        }

        // Delete Method
        redisTemplate.opsForSet().add("DemoSet",1);  // 注: redis中的值严格区分"1"和1
        del_res = redisTemplate.opsForSet().remove("DemoSet",1);  // 返回被删除的元素
        System.out.println(del_res);

        // Set Method
        set_res = redisTemplate.opsForSet().add("DemoSet",1); // 添加单个元素
        set_res = redisTemplate.opsForSet().add("DemoSet",new Integer[]{1,2,3,4,5});
        Collection<User> obj_res = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            obj_res.add(new User("Maxim",rand.nextInt(100)));
        }
        set_res = (Long) redisTemplate.opsForSet().add("DemoObject",obj_res);

        // Get Method
        get_res = (Boolean) redisTemplate.opsForSet().isMember("DemoSet",1);
        System.out.println(get_res);
        Set<Object> res_list = redisTemplate.opsForSet().members("DemoObject"); // 正确的获取方法
        get_res = (Boolean) redisTemplate.opsForSet().isMember("DemoObject",new User("Maxim",23));
        // get_res = (Boolean) res_list.contains(new User("Maxim",23));     // 判断指定元素是否在集合中
        System.out.println(get_res);
    }
}