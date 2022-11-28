package com.passingtest.repository;

import com.passingtest.model.entity.Test;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface TestRepository extends CrudRepository<Test, BigInteger> {

}
