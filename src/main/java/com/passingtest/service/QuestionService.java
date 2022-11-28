package com.passingtest.service;

import com.passingtest.exception.ObjectNotFoundException;
import com.passingtest.model.entity.Question;
import com.passingtest.repository.QuestionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
        List<Question> questions = new ArrayList<>();
        questionRepository.getQuestionsByTestId(testId).forEach(questions::add);
        return questions;
    }
}
