package com.passingtest.repository;

import com.passingtest.model.entity.Test;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<Test, Integer> {

}
