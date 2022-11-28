package com.passingtest.service;

import com.passingtest.model.entity.*;
import com.passingtest.repository.UserRepository;
import com.passingtest.repository.UserTestDetailRepository;
import com.passingtest.repository.UserTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTestRepository userTestRepository;

    @Autowired
    UserTestDetailRepository userTestDetailRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    private Map<UserTest, ArrayDeque<Question>> testQuestions = new HashMap<>();

    private Map<UserTest, Integer> numberCorrectQuestions = new HashMap<>();

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setUserTestRepository(UserTestRepository userTestRepository) {
        this.userTestRepository = userTestRepository;
    }

    public void setUserTestDetailRepository(UserTestDetailRepository userTestDetailRepository) {
        this.userTestDetailRepository = userTestDetailRepository;
    }

    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public Map<UserTest, ArrayDeque<Question>> getTestQuestions() {
        return testQuestions;
    }

    public Map<UserTest, Integer> getNumberCorrectQuestions() {
        return numberCorrectQuestions;
    }

    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    public void userSaveOrUpdate(User user) {
        userRepository.save(user);
    }

    public UserTest getUserTestById(BigInteger id) {
        return userTestRepository.findById(id).get();
    }

    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    public List<UserTest> getUserTestsByUserId(BigInteger userId) {
        List<UserTest> userTests = new ArrayList<UserTest>();
        userTestRepository.findByUserId(userId).forEach((userTest) -> userTests.add(userTest));
        return userTests;
    }

    public UserTest startUserTest(BigInteger userId, BigInteger testId) {
        UserTest userTest = new UserTest();
        userTest.setTestId(testId);
        userTest.setStarted(new Timestamp(System.currentTimeMillis()));
        userTest.setUserId(userId);
        userTest.setNumberCorrectQuestions(0);
        userTest = userTestRepository.save(userTest);
        numberCorrectQuestions.put(userTest, 0);
        setQuestions(userTest);
        return userTest;
    }

    public void continueUserTest(UserTest userTest) {
        if (userTest.getFinished() != null) {
            throw new RuntimeException("Тест был завершен, прохождение теста невозможно!");
        }
        numberCorrectQuestions.put(userTest, userTest.getNumberCorrectQuestions());
        setQuestions(userTest);
    }

    public void finishUserTest(UserTest userTest) {
        userTest.setFinished(new Timestamp(System.currentTimeMillis()));
        userTestRepository.save(userTest);
    }

    private void setQuestions(UserTest userTest) {
        List<Question> questionsAll = questionService.getQuestionsByTestId(userTest.getTestId());
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
        if (testQuestions.get(userTest) != null) {
            return testQuestions.get(userTest).peekFirst();
        }
        return null;
    }

    @Transactional
    public void saveAnswers(UserTest userTest, Question question, List<Answer> answers) {
        if ((testQuestions.get(userTest) == null) || (testQuestions.get(userTest) != null && testQuestions.get(userTest).size() == 0)) {
            throw new RuntimeException("Вопросы теста не найдены!");
        }
        if (!testQuestions.get(userTest).contains(question) || !userTest.getTestId().equals(question.getTestId())) {
            throw new RuntimeException("Вопрос не относится к данному тесту!");
        }
        if (answerService.isSelectedQuestionAnswersIsCorrect(question, answers)) {
            Integer numberQuestions = numberCorrectQuestions.get(userTest);
            numberQuestions++;
            numberCorrectQuestions.put(userTest, numberQuestions);
            userTest.setNumberCorrectQuestions(numberQuestions);
            userTestRepository.save(userTest);
        }
        saveDetail(userTest, question, answers);
        testQuestions.get(userTest).removeFirst();
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
