package com.passingtest.repository;

import com.passingtest.model.entity.UserTestDetail;
import org.springframework.data.repository.CrudRepository;

//repository that extends CrudRepository
public interface UserTestDetailRepository extends CrudRepository<UserTestDetail, Integer> {
}
