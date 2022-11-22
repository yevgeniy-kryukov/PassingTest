package com.passingtest.service;

import com.passingtest.model.entity.Question;
import com.passingtest.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    public Question getQuestionById(int id) {
        return questionRepository.findById(id).get();
    }

    public void saveOrUpdate(Question question) {
        questionRepository.save(question);
    }

    public List<Question> getQuestionsByTestId(Integer testId) {
        List<Question> questions = new ArrayList<Question>();
        questionRepository.getQuestionsByTestId(testId).forEach(question1 -> questions.add(question1));
        return questions;
    }
}
