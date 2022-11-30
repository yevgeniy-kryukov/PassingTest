package com.passingtest.service;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.passingtest.exception.ObjectNotFoundException;
import com.passingtest.model.entity.Question;
import com.passingtest.repository.QuestionRepository;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    public Question getQuestionById(BigInteger id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Question.class));
    }

    public void saveOrUpdate(Question question) {
        questionRepository.save(question);
    }

    public List<Question> getQuestionsByTestId(BigInteger testId) {
        return StreamSupport.stream(questionRepository.getQuestionsByTestId(testId).spliterator(), false)
                .collect(Collectors.toList());
    }
}
