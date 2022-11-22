package com.passingtest.service;

import com.passingtest.model.entity.Answer;
import com.passingtest.model.entity.Question;
import com.passingtest.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    AnswerRepository answerRepository;

    public Answer getAnswerById(int id) {
        return answerRepository.findById(id).get();
    }

    public void saveOrUpdate(Answer answer) {
        answerRepository.save(answer);
    }

    public List<Answer> getAnswersByQuestionId(Integer questionId) {
        return null;
    }

    public boolean isSelectedQuestionAnswersIsCorrect(Question question, List<Answer> selectedAnswers) {
        return false;
    }
}
