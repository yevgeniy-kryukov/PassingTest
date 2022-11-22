package com.passingtest.repository;

import com.passingtest.model.entity.Answer;
import org.springframework.data.repository.CrudRepository;

//repository that extends CrudRepository
public interface AnswerRepository extends CrudRepository<Answer, Integer> {
}
