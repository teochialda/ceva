package com.licenta.app.repositories;

import com.licenta.app.entities.Food;
import com.licenta.app.entities.UserExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExerciseRepository extends JpaRepository<UserExercise, Long> {

    @Modifying
    @Query(value = "DELETE FROM user_exercise WHERE user_id=:id",nativeQuery = true)
    int deleteUserById(Long id);
}
