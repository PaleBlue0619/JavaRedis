package com.maxim.springdataredisdemo;
import java.util.*;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.maxim.springdataredisdemo.pojo.User;
import com.maxim.springdataredisdemo.utils.RedisUtil;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.support.collections.DefaultRedisSet;

@SpringBootTest
public class SortedSetTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    void testSortedSet() {
        Random rand = new Random(42);
        Object del_res, set_res, get_res;

        if (redisTemplate.hasKey("DemoSortedSet")){
            redisTemplate.delete("DemoSortedSet");
        }
        if (redisTemplate.hasKey("DemoObject")){
            redisTemplate.delete("DemoObject");
        }

        // Delete Method
        set_res = redisTemplate.opsForZSet().add("DemoSortedSet","Obj1", rand.nextGaussian()); // score是value对应的分数, 用于排序
        del_res = redisTemplate.opsForZSet().remove("DemoSortedSet","Obj1");  // 返回被删除的元素
        System.out.println(del_res);

        // Set Method
        set_res = redisTemplate.opsForZSet().add("DemoSortedSet","Obj1",rand.nextGaussian()); // 添加单个元素及其分数
        set_res = redisTemplate.opsForZSet().add("DemoSortedSet", new User("Maxim", rand.nextInt(100)), rand.nextGaussian()); // 添加单个对象及其分数
        Set<ZSetOperations.TypedTuple<Object>> add_set = new HashSet<>();  // 添加多个元素以及对应分数
        for (int i=0; i < 50; i++){
            ZSetOperations.TypedTuple<Object> add_tuple= new DefaultTypedTuple<>("Obj"+String.valueOf(i), rand.nextGaussian());
            add_set.add(add_tuple);
        }
        set_res = (Long) redisTemplate.opsForZSet().add("DemoSortedSet", add_set);
        add_set.clear();
        for (int i=0; i < 50; i++){
            ZSetOperations.TypedTuple<Object> add_tuple= new DefaultTypedTuple<>(new User("Maxim", i), rand.nextGaussian());
            add_set.add(add_tuple);
        }
        set_res = (Long) redisTemplate.opsForZSet().add("DemoObject", add_set);

        // Get Method
        // 由于已经重写了equals()方法，所以这里可以传入对象进行查找
        // 查找指定元素的score值
        get_res = redisTemplate.opsForZSet().score("DemoSortedSet", "Obj1");
        get_res = redisTemplate.opsForZSet().score("DemoObject", new User("Maxim", 35));
        System.out.println(get_res);

        // 查找指定元素的rank值(在sortedSet中的排名)
        get_res = redisTemplate.opsForZSet().rank("DemoSortedSet", "Obj1");
        get_res = redisTemplate.opsForZSet().rank("DemoObject", new User("Maxim", 35));
        System.out.println("rank:"+get_res);

        // 按照score排序后, 获取score位于指定区间的元素
        Long elementNum = redisTemplate.opsForZSet().count("DemoSortedSet", -.5, .5); // 统计score位于指定区间的元素个数
        Set<Object> obj_list = redisTemplate.opsForZSet().rangeByScore("DemoObject",-5,5);
        Collection<String> names = obj_list.stream().map(obj -> ((User) obj).getName()).collect(Collectors.toList());
        Collection<String> ages = obj_list.stream().map(obj -> ((User) obj).getAge().toString()).collect(Collectors.toList());

        // 按照score排序后, 获取指定索引区间(排名区间)的元素个数
        get_res = redisTemplate.opsForZSet().range("DemoSortedSet",0,10); // 正序排序
        get_res = redisTemplate.opsForZSet().reverseRange("DemoSortedSet",0,10); // 倒序排序
        obj_list = redisTemplate.opsForZSet().range("DemoObject",0,10); // 正序排序
        obj_list = redisTemplate.opsForZSet().reverseRange("DemoObject",0,10); // 倒序排序
        names = obj_list.stream().map(obj -> ((User) obj).getName()).collect(Collectors.toList());
        ages = obj_list.stream().map(obj -> ((User) obj).getAge().toString()).collect(Collectors.toList());
        System.out.println(ages);
    }
}
