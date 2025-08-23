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
        if (!redisTemplate.hasKey("name")){
            redisTemplate.opsForValue().set("name", "Maxim");
        }  // String 类型
        System.out.println(redisTemplate.opsForValue().get("name"));

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
