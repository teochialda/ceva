package com.licenta.app.controllers;

import com.licenta.app.dtos.UserExerciseDTO;
import com.licenta.app.dtos.UserFoodDTO;
import com.licenta.app.entities.UserExercise;
import com.licenta.app.entities.UserFood;
import com.licenta.app.services.UserExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/userexercises")
public class UserExerciseController {

    private final UserExerciseService userExerciseService;

    @Autowired
    public UserExerciseController(UserExerciseService userExerciseService) {
        this.userExerciseService = userExerciseService;
    }

    @GetMapping("/{userId}")
    public UserExercise getUserExerciseById(@PathVariable Long userId) {
        return userExerciseService.getUserById(userId);
    }

    @GetMapping
    public List<UserExerciseDTO> getAllUserFoods() {
        List<UserExerciseDTO> userExercises = userExerciseService.getAllUserExercises();
        return  userExercises;
    }

}
