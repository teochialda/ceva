package com.licenta.app.controllers;

import com.licenta.app.dtos.ExerciseDTO;
import com.licenta.app.dtos.FoodDTO;
import com.licenta.app.dtos.UserDTO;
import com.licenta.app.enums.MomentOfTheDay;
import com.licenta.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/{userId}/foods/{foodId}/{momentOfTheDay}/{quantityInGrams}")
    public void addFoodToUser(@PathVariable Long userId, @PathVariable Long foodId, @PathVariable MomentOfTheDay momentOfTheDay, @PathVariable int quantityInGrams) {
        userService.addFoodToUser(userId, foodId, momentOfTheDay, quantityInGrams);
    }

    @PostMapping("/{userId}/exercises/{exerciseId}/{repsOrTime}")
    public void addExerciseToUser(@PathVariable Long userId, @PathVariable Long exerciseId, @PathVariable int repsOrTime) {
        userService.addExerciseToUser(userId, exerciseId, repsOrTime);
    }

    @GetMapping()
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("login/{name}/{password}")
    public UserDTO login(@PathVariable String name,@PathVariable String password) {
        return userService.login(name, password);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/{userId}/get-foods")
    public List<FoodDTO> getUserFoods(@PathVariable Long userId) {
        return userService.getUserFoods(userId);
    }

    @GetMapping("/{userId}/get-exercises")
    public List<ExerciseDTO> getUserExercises(@PathVariable Long userId) {
        return userService.getUserExercises(userId);
    }

    @PostMapping("/clearFoodRecords/{userId}")
    public ResponseEntity<String> deleteFoodRecords(@PathVariable Long userId) {
        userService.clearFoodRecords(userId);
        return ResponseEntity.ok("Food cleared successfully");
    }

    @PostMapping("/clearExerciseRecords/{userId}")
    public ResponseEntity<String> deleteExerciseRecords(@PathVariable Long userId) {
        userService.clearExercisesRecords(userId);
        return ResponseEntity.ok("Exercise cleared successfully");
    }

    @PostMapping("/clearAllRecords/{userId}")
    public ResponseEntity<String> deleteAllRecords(@PathVariable Long userId) {
        userService.clearExercisesRecords(userId);
        userService.clearFoodRecords(userId);
        return ResponseEntity.ok("Exercise cleared successfully");
    }
}
