package com.licenta.app.repositories;

import com.licenta.app.entities.Food;
import com.licenta.app.entities.UserFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFoodRepository extends JpaRepository<UserFood, Long> {

    @Modifying
    @Query(value = "DELETE FROM user_food WHERE user_id=:id",nativeQuery = true)
    int deleteUserById(Long id);
}
