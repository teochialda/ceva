package com.licenta.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "user_exercise")
public class UserExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Column(name = "reps_or_time")
    private int repsOrTime;

    public UserExercise() {
    }

    public UserExercise(User user, Exercise exercise) {
        this.user = user;
        this.exercise = exercise;
    }

    public UserExercise(User user, Exercise exercise, int repsOrTime) {
        this.user = user;
        this.exercise = exercise;
        this.repsOrTime = repsOrTime;
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

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public int getRepsOrTime() {
        return repsOrTime;
    }

    public void setRepsOrTime(int repsOrTime) {
        this.repsOrTime = repsOrTime;
    }
}
