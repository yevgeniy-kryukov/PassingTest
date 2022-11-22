package com.passingtest.repository;

import com.passingtest.model.entity.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

//repository that extends CrudRepository
public interface QuestionRepository extends CrudRepository<Question, Integer> {
    @Query("FROM Question WHERE testId=:testId")
    public Iterable<Question> getQuestionsByUserId(@Param("testId") Integer testId);
}
