package com.licenta.app.services;

import com.licenta.app.dtos.ExerciseDTO;
import com.licenta.app.dtos.FoodDTO;
import com.licenta.app.dtos.UserDTO;
import com.licenta.app.entities.*;
import com.licenta.app.enums.MomentOfTheDay;
import com.licenta.app.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final FoodRepository foodRepository;

    private final ExerciseRepository exerciseRepository;

    private final UserFoodRepository userFoodRepository;

    private final UserExerciseRepository userExerciseRepository;

    @Autowired
    public UserService(UserRepository userRepository, FoodRepository foodRepository, ExerciseRepository exerciseRepository, UserFoodRepository userFoodRepository, UserExerciseRepository userExerciseRepository) {
        this.userRepository = userRepository;
        this.foodRepository = foodRepository;
        this.exerciseRepository = exerciseRepository;
        this.userFoodRepository = userFoodRepository;
        this.userExerciseRepository = userExerciseRepository;
    }
    public UserDTO createUser(UserDTO userDTO) {
        User user = buildUserEntityFromDTO(userDTO);
        User savedUser = userRepository.save(user);
        return buildUserDTOFromEntity(savedUser);
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            updateFieldsFromDTO(user, userDTO);
            User updatedUser = userRepository.save(user);
            return buildUserDTOFromEntity(updatedUser);
        }
        throw new RuntimeException("User not found with ID: " + userId);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<UserDTO> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream().map(UserService::buildUserDTOFromEntity).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return buildUserDTOFromEntity(user);
        }
        throw new RuntimeException("User not found with ID: " + userId);
    }

    public void addFoodToUser(Long userId, Long foodId, MomentOfTheDay momentOfTheDay, int quantityInGrams) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Food> optionalFood = foodRepository.findById(foodId);
            if (optionalFood.isPresent()) {
                Food food = optionalFood.get();

                UserFood userFood = new UserFood(user, food, quantityInGrams);
                userFood.setAdditionalColumn(momentOfTheDay.toString());

                user.getFoods().add(userFood);

                userFoodRepository.save(userFood);
                int updatedCalories;
                updatedCalories = user.getCalories() + (food.getCalories() * quantityInGrams / 100);
                user.setCalories(updatedCalories);

                userRepository.save(user);
            } else {
                throw new RuntimeException("Food not found with ID: " + foodId);
            }
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    public void addExerciseToUser(Long userId, Long exerciseId, int repsOrTime) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Optional<Exercise> optionalExercise = exerciseRepository.findById(exerciseId);
            if (optionalExercise.isPresent()) {
                Exercise exercise = optionalExercise.get();

                UserExercise userExercise = new UserExercise(user, exercise, repsOrTime);

                user.getExercises().add(userExercise);
                userExerciseRepository.save(userExercise);

                int updatedCalories = user.getCalories() - (exercise.getCalories() * repsOrTime);

                user.setCalories(updatedCalories);
                userRepository.save(user);
            } else {
                throw new RuntimeException("Exercise not found with ID: " + exerciseId);
            }
        } else {
            throw new RuntimeException("User not found with ID: " + userId);
        }
    }

    public static User buildUserEntityFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setCalories(userDTO.getCalories());
        user.setKilograms(userDTO.getKilograms());
        user.setAge(userDTO.getAge());
        user.setPassword(userDTO.getPassword());
        if (userDTO.getFoods() != null) {
            List<Food> foodSet = userDTO.getFoods().stream().map(FoodService::getById).collect(Collectors.toList());
            foodSet.stream().forEach( food -> {
                UserFood u = new UserFood(user, food);
                user.getFoods().add(u);
            });
        }
        if (userDTO.getExercises() != null) {
            List<Exercise> exerciseList = userDTO.getExercises().stream().map(ExerciseService::getById).collect(Collectors.toList());
            exerciseList.stream().forEach( exercise -> {
                UserExercise u = new UserExercise(user, exercise);
                user.getExercises().add(u);
            });
        }
        return user;
    }

    public static UserDTO buildUserDTOFromEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword());
        userDTO.setAge(user.getAge());
        userDTO.setCalories(user.getCalories());
        userDTO.setKilograms(user.getKilograms());
        List<Long> fid = new ArrayList<>();
        user.getFoods().stream().forEach(userFood -> {
            fid.add(userFood.getFood().getId());
        });
        userDTO.setFoods(fid);
        List<Long> eid = new ArrayList<>();
        user.getExercises().stream().forEach(userExercise -> {
            eid.add(userExercise.getExercise().getId());
        });
        userDTO.setExercises(eid);
        return userDTO;
    }

    private void updateFieldsFromDTO(User user, UserDTO userDTO) {
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        user.setCalories(userDTO.getCalories());
        user.setKilograms(userDTO.getKilograms());
        Set<Food> foodSet = userDTO.getFoods().stream().map(FoodService::getById).collect(Collectors.toSet());
        foodSet.stream().forEach(food -> {
            UserFood u = new UserFood(user, food);
            user.getFoods().add(u);

        });
        Set<Exercise> exerciseSet = userDTO.getExercises().stream().map(ExerciseService::getById).collect(Collectors.toSet());
        exerciseSet.stream().forEach(exercise -> {
            UserExercise u = new UserExercise(user, exercise);
            user.getExercises().add(u);
        });
    }

    public UserDTO login(String username, String password) {
        User user = userRepository.findByNameAndPassword(username, password);
        if (user == null) {
            throw new RuntimeException("Invalid username or password");
        }
        // Map the User entity to UserDTO
        UserDTO userDTO = buildUserDTOFromEntity(user);

        return userDTO;
    }

    public List<FoodDTO> getUserFoods(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<FoodDTO> foodDTOS = new ArrayList<>();
            for (UserFood userFood : user.get().getFoods()) {
                Food f = userFood.getFood();
                FoodDTO foodDTO = FoodService.buildFoodDTOFromEntity(f);
                foodDTOS.add(foodDTO);
            }
            return foodDTOS;
        }
        throw new RuntimeException("Invalid userId");
    }

    public List<ExerciseDTO> getUserExercises(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<ExerciseDTO> exerciseDTOS = new ArrayList<>();
            for (UserExercise userExercise : user.get().getExercises()) {
                Exercise e = userExercise.getExercise();
                ExerciseDTO exerciseDTO = ExerciseService.buildExerciseDTOFromEntity(e);
                exerciseDTOS.add(exerciseDTO);
            }
            return exerciseDTOS;
        }
        throw new RuntimeException("Invalid userId");
    }

    public void clearFoodRecords(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userOptional.get().setCalories(0);
            userFoodRepository.deleteUserById(userId);
        }
    }

    public void clearExercisesRecords(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            userOptional.get().setCalories(0);
            userExerciseRepository.deleteUserById(userId);
        }
    }
}