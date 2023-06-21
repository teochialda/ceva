package com.licenta.app.dtos;

import com.licenta.app.entities.UserExercise;

public class UserExerciseDTO {

    private UserDTO user;

    private ExerciseDTO exercise;

    private int repsOrTime;

    public UserExerciseDTO(UserDTO user, ExerciseDTO exercise, int repsOrTime) {
        this.user = user;
        this.exercise = exercise;
        this.repsOrTime = repsOrTime;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ExerciseDTO getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseDTO exercise) {
        this.exercise = exercise;
    }

    public int getRepsOrTime() {
        return repsOrTime;
    }

    public void setRepsOrTime(int repsOrTime) {
        this.repsOrTime = repsOrTime;
    }
}
