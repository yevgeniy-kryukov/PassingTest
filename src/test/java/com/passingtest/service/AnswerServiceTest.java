package com.passingtest.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.passingtest.model.entity.Answer;
import com.passingtest.model.entity.Question;

class AnswerServiceTest {
    AnswerService answerService;

    @BeforeEach
    void setUp() {
        answerService = new AnswerService();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAnswerById() {
    }

    @Test
    void saveOrUpdate() {
    }

    @Test
    void getAnswersByQuestionId() {
    }

    @Test
    void isSelectedQuestionAnswersIsCorrect() {
        Answer answer1Correct  = createAnswer(1, true);
        Answer answer2  = createAnswer(2, false);
        Answer answer3Correct  = createAnswer(3, true);
        Answer answer4  = createAnswer(4, false);
        Answer answer5  = createAnswer(5, false);
        List<Answer> listAnswers = Arrays.asList(answer1Correct, answer2, answer3Correct, answer4, answer5);
        Question question = new Question();
        question.setAnswers(listAnswers);

        List<Answer> selectedAnswersAllCorrectedTheSameOrder = Arrays.asList(createAnswer(1, null), createAnswer(3, null));
        assertTrue(answerService.isSelectedQuestionAnswersIsCorrect(question, selectedAnswersAllCorrectedTheSameOrder));

        List<Answer> selectedAnswersAllCorrectedTheDifferentOrder = Arrays.asList(createAnswer(3, null), createAnswer(1, null));
        assertTrue(answerService.isSelectedQuestionAnswersIsCorrect(question, selectedAnswersAllCorrectedTheDifferentOrder));

        List<Answer> selectedAnswersNumberIsMore = Arrays.asList(
                createAnswer(1, null),
                createAnswer(2, null),
                createAnswer(3, null)
        );
        assertFalse(answerService.isSelectedQuestionAnswersIsCorrect(question, selectedAnswersNumberIsMore));

        List<Answer> selectedAnswersNumberIsLess = Arrays.asList(createAnswer(1, true));
        assertFalse(answerService.isSelectedQuestionAnswersIsCorrect(question, selectedAnswersNumberIsLess));
    }

    private Answer createAnswer(long id, Boolean correct) {
        Answer answer = new Answer();
        answer.setId(BigInteger.valueOf(id));
        answer.setCorrect(correct);
        return answer;
    }
}
