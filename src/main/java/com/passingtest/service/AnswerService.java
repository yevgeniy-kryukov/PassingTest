package com.passingtest.service;

import com.passingtest.exception.ObjectNotFoundException;
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

    public Answer getAnswerById(int id) throws ObjectNotFoundException {
        return answerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Answer.class));
    }

    public void saveOrUpdate(Answer answer) {
        answerRepository.save(answer);
    }

    public List<Answer> getAnswersByQuestionId(Integer questionId) {
        return answerRepository.findByAnswersByQuestionId(questionId);
    }

    public boolean isSelectedQuestionAnswersIsCorrect(Question question, List<Answer> selectedAnswers) {
        if (selectedAnswers.isEmpty()) {
            throw new RuntimeException("Как минимум 1 ответ должен быть выбран!");
        }
        //before java 8
/*

        List<Answer> correctAnswers = question.getAnswers().stream()
                .filter(Answer::getCorrect)
                .collect(Collectors.toList());
        if (correctAnswers.size() != selectedAnswers.size()) {
            return false;
        }
        for (int i = 0; i < correctAnswers.size(); i++) {
            for (int j = 0; j < selectedAnswers.size(); j++) {
                Answer selectedAnswer = selectedAnswers.get(j);
                if (selectedAnswer.getId().equals(correctAnswers.get(i).getId())) {
                    break;
                }
                if (j == selectedAnswers.size() - 1) {
                    return false;
                }
            }
        }
        return true;
*/
        long correctAnswersCount = question.getAnswers().stream().filter(Answer::getCorrect).count();
        return correctAnswersCount == selectedAnswers.size() &&
                question.getAnswers().stream()
                        .filter(Answer::getCorrect)
                        .map(Answer::getId)
                        .anyMatch(correctAnswerId ->
                                selectedAnswers.stream().map(Answer::getId).distinct()
                                        .anyMatch(selectedAnswerId -> selectedAnswerId.equals(correctAnswerId))
                        );

    }
}
