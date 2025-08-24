package com.maxim.springdataredisdemo.pojo;

import java.util.Objects;

public class User {
    private String name = "AAA";
    private Integer age = 20;

    public User() {
    }
    /*
    * Jackson在反序列化Redis中的JSON数据为User对象时，找不到可用的构造器（如无参构造函数），导致无法实例化对象。
      详细原因分析：
        User类没有定义无参构造函数。
        Jackson默认使用无参构造函数配合setter方法来反序列化对象
    * */

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }


    // 确保contains能够正确判断对象是否在集合中, 重写equals & hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(name, user.name) &&
                Objects.equals(age, user.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
