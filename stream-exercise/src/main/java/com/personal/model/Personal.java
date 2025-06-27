package com.personal.model;

import java.util.List;

public class Personal {

    private String name;
    private int age;
    private Gender gender;
    private List<Pets> pet;

    public Personal(String name, int age, Gender gender, List<Pets> pet) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.pet = pet;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public List<Pets> getPet() {
        return pet;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setPet(List<Pets> pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return " Person [name: " + name + ", gender: " + ", Age: "+ age + ", Pet: " + " ]";
    }
}
