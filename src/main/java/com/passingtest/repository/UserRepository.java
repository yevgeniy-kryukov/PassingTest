package com.passingtest.repository;

import com.passingtest.model.entity.User;
import com.passingtest.model.entity.UserTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface UserRepository extends CrudRepository<User, BigInteger> {
    @Query("FROM User WHERE Name=:name")
    public Iterable<User> findByName(@Param("name") String name);
}
