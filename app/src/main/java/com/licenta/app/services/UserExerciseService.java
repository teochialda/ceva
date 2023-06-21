package com.licenta.app.services;


import com.licenta.app.dtos.UserExerciseDTO;
import com.licenta.app.entities.UserExercise;
import com.licenta.app.repositories.UserExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserExerciseService {

    private final UserExerciseRepository userExerciseRepository;

    @Autowired
    public UserExerciseService(UserExerciseRepository userExerciseRepository) {
        this.userExerciseRepository = userExerciseRepository;
    }

    public UserExercise getUserById(Long userId) {
        Optional<UserExercise> optionalExercise = userExerciseRepository.findById(userId);
        if (optionalExercise.isPresent()) {
            return optionalExercise.get();
        }
        throw new RuntimeException("User not found with ID: " + userId);
    }

    public List<UserExerciseDTO> getAllUserExercises() {
        List<UserExercise> userExercises = userExerciseRepository.findAll();

        return userExercises.stream().map(UserExerciseService::fromEntityToDTO).collect(Collectors.toList());
    }

    public static UserExerciseDTO fromEntityToDTO(UserExercise userExercise) {
        UserExerciseDTO userExerciseDTO = new UserExerciseDTO(UserService.buildUserDTOFromEntity(userExercise.getUser()), ExerciseService.buildExerciseDTOFromEntity(userExercise.getExercise()), userExercise.getRepsOrTime());
        return userExerciseDTO;
    }

}
