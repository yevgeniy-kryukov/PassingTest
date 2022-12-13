package com.passingtest.service;

import com.passingtest.exception.AppAuthException;
import com.passingtest.exception.AppNotFoundException;
import com.passingtest.exception.AppValidationException;
import com.passingtest.exception.ObjectNotFoundException;
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

    @Autowired
    TestService testService;

    private final Map<UserTest, ArrayDeque<Question>> testQuestions = new HashMap<>();

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

    public User getUserById(BigInteger id) {
        return userRepository.findById(id).get();
    }

    public void userSaveOrUpdate(User user) {
        userRepository.save(user);
    }

    public UserTest getUserTestById(BigInteger id) {
        return userTestRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, UserTest.class));
    }

    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    public void setTestService(TestService testService) {
        this.testService = testService;
    }

    public List<UserTest> getUserTestsByUserId(BigInteger userId) {
        List<UserTest> userTests = new ArrayList<UserTest>();
        userTestRepository.findByUserId(userId).forEach(userTests::add);
        return userTests;
    }

    public UserTest startUserTest(BigInteger userId, BigInteger testId) {
        UserTest userTest = new UserTest();
        userTest.setTestId(testId);
        userTest.setTest(testService.getTestById(testId));
        userTest.setStarted(new Timestamp(System.currentTimeMillis()));
        userTest.setUserId(userId);
        userTest.setNumberCorrectQuestions(0);
        userTest = userTestRepository.save(userTest);
        setQuestions(userTest);
        return userTest;
    }

    public UserTest continueUserTest(UserTest userTest) throws AppValidationException {
        if (userTest.getFinished() != null) {
            throw new AppValidationException("Тест был завершен, прохождение теста невозможно!");
        }
        setQuestions(userTest);
        return userTest;
    }

    private boolean getIsTestPassed(Integer numberCorrectQuestions, Short minLevelCorrect) {
        return numberCorrectQuestions >= minLevelCorrect;
    }

    public UserTest finishUserTest(UserTest userTest) throws AppValidationException {
        if (userTest.getFinished() != null) {
            throw new AppValidationException("Тест уже завершен!");
        }
        userTest.setFinished(new Timestamp(System.currentTimeMillis()));
        userTest.setTestPassed(getIsTestPassed(userTest.getNumberCorrectQuestions(), userTest.getTest().getMinLevelCorrect()));
        testQuestions.remove(userTest);
        userTestRepository.save(userTest);
        return userTest;
    }

    @Transactional(readOnly = true)
    private void setQuestions(UserTest userTest) {
        List<Question> questionsAll = questionService.getQuestionsByTestId(userTest.getTestId());
        List<UserTestDetail> userTestDetails = Optional.ofNullable(userTest.getUserTestDetails()).orElse(Collections.emptyList());
        ArrayDeque<Question> questionArrayDeque = new ArrayDeque<Question>();

        for1:
        for (Question question : questionsAll) {
            for (UserTestDetail userTestDetail : userTestDetails) {
                if (userTestDetail.getQuestionId().equals(question.getId())) {
                    continue for1;
                }
            }
            //загружаем ответы (FetchType.LAZY), объекты должны быть загружены в слое Service
            //используем аннотацию @Transactional, чтобы сессия БД закрывалась только после выполнения метода
            //https://stackoverflow.com/questions/15359306/how-to-fetch-fetchtype-lazy-associations-with-jpa-and-hibernate-in-a-spring-cont
            if (question.getAnswers() != null) {
                question.getAnswers().size();
            }
            questionArrayDeque.add(question);
        }

        if (testQuestions.get(userTest) != null) testQuestions.remove(userTest);
        testQuestions.put(userTest, questionArrayDeque);
    }

    public Question getNextQuestion(UserTest userTest) throws AppNotFoundException {
        if (testQuestions.get(userTest) == null) {
            throw new AppNotFoundException("Вопросы теста не найдены! Тест еще не начат или тест уже завершен.");
        }
        return testQuestions.get(userTest).peekFirst();
    }

    @Transactional
    public void saveAnswers(UserTest userTest, Question question, List<Answer> answers) throws AppValidationException, AppNotFoundException {
        if ((testQuestions.get(userTest) == null) || (testQuestions.get(userTest) != null && testQuestions.get(userTest).size() == 0)) {
            throw new AppNotFoundException("Вопросы теста не найдены! Тест еще не начат или тест уже завершен.");
        }
        if (!testQuestions.get(userTest).contains(question) || !userTest.getTestId().equals(question.getTestId())) {
            throw new AppValidationException("Вопрос не относится к данному тесту или на вопрос уже был дан ответ!");
        }
        if (answerService.isSelectedQuestionAnswersIsCorrect(question, answers)) {
            Integer numberQuestions = userTest.getNumberCorrectQuestions();
            numberQuestions++;
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

    public User userAuthentication(String userName, String pwd) throws AppAuthException {
        Iterable<User> userIterable = userRepository.findByName(userName);
        if (!userIterable.iterator().hasNext()) {
            throw new AppAuthException("Ошибка! Пользователь с таким именем не найден");
        } else {
            User user = userIterable.iterator().next();
            if (!user.getPwd().equals(pwd)) {
                throw new AppAuthException("Ошибка! Пароль не верный");
            } else {
                return user;
            }
        }
    }
}
