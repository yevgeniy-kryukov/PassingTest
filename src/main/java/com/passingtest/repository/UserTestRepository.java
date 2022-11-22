package com.passingtest.repository;

import com.passingtest.model.entity.UserTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserTestRepository extends CrudRepository<UserTest, Integer> {
    @Query("FROM UserTest WHERE userId = :userId")
    public Iterable<UserTest> findByUserId(@Param("userId") Integer userId);
}
