package com.passingtest.repository;

import com.passingtest.model.entity.User;
import org.springframework.data.repository.CrudRepository;

//repository that extends CrudRepository
public interface UserRepository extends CrudRepository<User, Integer> {
}