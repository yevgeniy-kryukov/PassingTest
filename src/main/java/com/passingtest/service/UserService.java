package com.passingtest.service;

import com.passingtest.model.entity.*;
import com.passingtest.repository.UserRepository;
import com.passingtest.repository.UserTestDetailRepository;
import com.passingtest.repository.UserTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTestRepository userTestRepository;

    @Autowired
    UserTestDetailRepository userTestDetailRepository;

    private HashMap<UserTest, ArrayDeque<Question>> testQuestions;

    private HashMap<UserTest, Integer> numberCorrectQuestions;

    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    public void userSaveOrUpdate(User user) {
        userRepository.save(user);
    }

    public UserTest getUserTestById(Integer id) {
        return userTestRepository.findById(id).get();
    }

    public List<UserTest> getUserTestsByUserId(Integer userId) {
        List<UserTest> userTests = new ArrayList<UserTest>();
        userTestRepository.findByUserId(userId).forEach((userTest) -> userTests.add(userTest));
        return userTests;
    }

    public UserTest startUserTest(Integer userId, Integer testId) {
        UserTest userTest = new UserTest();
        userTest.setTestId(testId);
        userTest.setStarted(new Timestamp(System.currentTimeMillis()));
        userTest.setUserId(userId);
        userTest.setNumberCorrectQuestions(0);
        userTestRepository.save(userTest);
        setQuestions(userTest);
        return userTest;
    }

    public void continueUserTest(UserTest userTest) {
        setQuestions(userTest);
    }

    public void finishUserTest(UserTest userTest) {
        userTest.setFinished(new Timestamp(System.currentTimeMillis()));
        userTestRepository.save(userTest);
    }

    private void setQuestions(UserTest userTest) {
        List<Question> questionsAll = new QuestionService().getQuestionsByTestId(userTest.getTestId());
        List<UserTestDetail> userTestDetails = userTest.getUserTestDetails();
        ArrayDeque<Question> questionArrayDeque = new ArrayDeque<Question>();

        for1:
        for (Question question : questionsAll) {
            for (UserTestDetail userTestDetail : userTestDetails) {
                if (userTestDetail.getQuestionId().equals(question.getId())) {
                    continue for1;
                }
            }
            questionArrayDeque.add(question);
        }

        testQuestions.put(userTest, questionArrayDeque);
    }

    public Question getNextQuestion(UserTest userTest) {
        return testQuestions.get(userTest).pollFirst();
    }

    @Transactional
    public void saveAnswers(UserTest userTest, Question question, List<Answer> answers) {
        AnswerService answerService = new AnswerService();
        if (answerService.isSelectedQuestionAnswersIsCorrect(question, answers)) {
            Integer numberQuestions = numberCorrectQuestions.get(userTest);
            numberQuestions++;
            numberCorrectQuestions.put(userTest, numberQuestions);
            userTest.setNumberCorrectQuestions(numberQuestions);
            userTestRepository.save(userTest);
        }
        saveDetail(userTest, question, answers);
    }

    private void saveDetail(UserTest userTest, Question question, List<Answer> answers) {
        for (Answer answer : answers) {
            UserTestDetail userTestDetail = new UserTestDetail();
            userTestDetail.setUserTestId(userTest.getId());
            userTestDetail.setQuestionId(question.getId());
            userTestDetail.setAnswerId(answer.getId());
            userTestDetailRepository.save(userTestDetail);
        }
    }

    public boolean userAuthentication(String userName, String pwd) {
        return false;
    }
}
