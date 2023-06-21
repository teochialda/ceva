package com.licenta.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.licenta.app.enums.MomentOfTheDay;
import jakarta.persistence.*;

@Entity
@Table(name = "user_food")
public class UserFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @Column(name = "moment_of_the_day")
    private String additionalColumn;

    @Column(name = "quantity_in_grams")
    private int quantityInGrams;

    public UserFood() {
    }

    public UserFood(User user, Food food) {
        this.user = user;
        this.food = food;
    }

    public UserFood(User user, Food food, int quantityInGrams) {
        this.user = user;
        this.food = food;
        this.quantityInGrams = quantityInGrams;
    }

    public UserFood(Long id, User user, Food food, MomentOfTheDay additionalColumn) {
        this.id = id;
        this.user = user;
        this.food = food;
        this.additionalColumn = additionalColumn.toString();
    }

    public void setAdditionalColumn(String additionalColumn) {
        this.additionalColumn = additionalColumn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getAdditionalColumn() {
        return additionalColumn;
    }

    public int getQuantityInGrams() {
        return quantityInGrams;
    }

    public void setQuantityInGrams(int quantityInGrams) {
        this.quantityInGrams = quantityInGrams;
    }
}
