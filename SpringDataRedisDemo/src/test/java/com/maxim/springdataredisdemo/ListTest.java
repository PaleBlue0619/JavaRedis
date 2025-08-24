package com.maxim.springdataredisdemo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.maxim.springdataredisdemo.pojo.User;
import com.maxim.springdataredisdemo.utils.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
public class ListTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;  // 注入RedisTemplate

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void testString(){
        Random rand = new Random(42);
        Object del_res, set_res, get_res;

        // delete Method (getAndDelete[本质上就是pop])
        if (redisTemplate.hasKey("DemoList")){
            redisTemplate.delete("DemoList");
        }
        if (redisTemplate.hasKey("DemoObject")){
            redisTemplate.delete("DemoObject");
        }
        redisUtil.lSet("DemoList", "1");
        del_res = redisTemplate.opsForList().leftPop("DemoList");  // 返回被删除的元素

        // put Method
        redisTemplate.opsForList().leftPush("DemoList", "0");
        redisTemplate.opsForList().rightPush("DemoList", "1");
        set_res = redisTemplate.opsForList().rightPushIfPresent("DemoList","1");  // 添加单个元素
        set_res = redisTemplate.opsForList().rightPush("DemoObject", new User("Maxim",23)); // 添加单个对象
        set_res = redisTemplate.opsForList().rightPushAll("DemoList", new String[]{"1","2","3"}); // 批量添加元素
        Collection<User> obj_list = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            obj_list.add(new User("Maxim",rand.nextInt(100)));
        }
        set_res = redisTemplate.opsForList().rightPushAll("DemoObject", obj_list);

        // get Method
        get_res = redisTemplate.opsForList().index("DemoList", 0);      // 查询指定索引的元素
        get_res = redisTemplate.opsForList().range("DemoList", 0, -1); // 查询指定区间索引的元素
        // User obj_res = (User) redisTemplate.opsForList().getLast("DemoObject");  // 错误的获取方法!!!
        List<Object> res_list = redisTemplate.opsForList().range("DemoObject", 0, -1); // 正确的获取方法
        User obj_res = (User) res_list.get(0);
        System.out.println(obj_res.toString());
    }
}