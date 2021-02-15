package com.charlie.course9;

import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/2/5
 */

// @Component("byComponentAnnotation")
public class Person {

    private Long id;

    private String name;

    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        ArrayList<String> one = Lists.newArrayList("one");
        System.out.println(one);
    }
}
