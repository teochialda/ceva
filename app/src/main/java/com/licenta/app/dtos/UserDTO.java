package com.licenta.app.dtos;

import java.util.List;
import java.util.Set;

public class UserDTO {

    private Long id;
    private String name;

    private String password;

    private int age;
    private int calories;
    private double kilograms;
    private List<Long> foods;
    private List<Long> exercises;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, List<Long> foods, List<Long> exerciseIds, double kilograms, int calories, int age) {
        this.id = id;
        this.name = name;
        this.foods = foods;
        this.exercises = exerciseIds;
        this.calories = calories;
        this.age = age;
        this.kilograms = kilograms;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getKilograms() {
        return kilograms;
    }

    public void setKilograms(double kilograms) {
        this.kilograms = kilograms;
    }

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

    public List<Long> getFoods() {
        return foods;
    }

    public void setFoods(List<Long> foods) {
        this.foods = foods;
    }

    public List<Long> getExercises() {
        return exercises;
    }

    public void setExercises(List<Long> exerciseIds) {
        this.exercises = exerciseIds;
    }
}
