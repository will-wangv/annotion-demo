package com.study.model;


import com.study.annotation.Study;

@Study(name = "tony", mores = {"11"}, type = 2)
public class Person {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Study(name = "kane", type = 99, mores = {"hard", "easy"})
    private String name;

    private int age;

    @Study(name = "kane", type = 99, mores = {"hard", "easy"})
    public String getString() {
        return "str";
    }

}
