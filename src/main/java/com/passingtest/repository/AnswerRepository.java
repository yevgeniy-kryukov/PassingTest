package com.passingtest.repository;

import java.math.BigInteger;
import java.util.List;
import com.passingtest.model.entity.Answer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends CrudRepository<Answer, BigInteger> {

    @Query("FROM Answer WHERE questionId =: questionId")
    List<Answer> findByAnswersByQuestionId(@Param("questionId") BigInteger questionId);
}
