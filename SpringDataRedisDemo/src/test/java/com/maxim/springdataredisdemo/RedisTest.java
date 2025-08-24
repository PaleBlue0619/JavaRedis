package com.maxim.springdataredisdemo;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;  // 注入RedisTemplate
    @Test
    void testBasic() {
        Random rand = new Random(42);

        // 插入数据
        if (!redisTemplate.hasKey("name")){  // 判断Key是否存在
            redisTemplate.opsForValue().set("name", "Maxim");
        }  // String 类型
        Object value = redisTemplate.opsForValue().get("name"); // 获取键
        Boolean setRes1 = redisTemplate.opsForValue().setIfAbsent("name", "Maxim"); // 如果不存在键则插入
        Boolean setRes2 = redisTemplate.opsForValue().setIfPresent("name", "Maxim"); // 如果存在键则更新

        if (!redisTemplate.hasKey("DemoList")){
            redisTemplate.opsForList().leftPush("DemoList", "0");
        } // List 类型
        System.out.println(redisTemplate.opsForList().leftPop("DemoList"));

        if (!redisTemplate.hasKey("DemoSet")){
            redisTemplate.opsForSet().add("DemoSet", "0");
        } // Set 类型
        System.out.println(redisTemplate.opsForSet().pop("DemoSet"));

        if (!redisTemplate.hasKey("DemoSortedSet")){
            redisTemplate.opsForZSet().add("DemoSortedSet", "0", rand.nextDouble());
            redisTemplate.opsForZSet().add("DemoSortedSet", "1", rand.nextGaussian());
        }
        System.out.println(redisTemplate.opsForZSet().popMin("DemoSortedSet"));
    }
}
