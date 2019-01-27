package com.netty.demo.vo;

import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * Created by wangjian on 2018/10/20.
 */
@Message  // msgPack是通过该注解做识别的
public class Member implements Serializable {

    private String name;

    private Integer age;

    private Double salary;

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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                '}';
    }
}
