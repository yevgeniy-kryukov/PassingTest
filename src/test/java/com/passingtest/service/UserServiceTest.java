package com.passingtest.service;

import static org.junit.jupiter.api.Assertions.*;

import com.passingtest.model.entity.Question;
import com.passingtest.model.entity.User;
import com.passingtest.model.entity.UserTest;
import com.passingtest.model.entity.UserTestDetail;
import com.passingtest.repository.UserRepository;
import com.passingtest.repository.UserTestDetailRepository;
import com.passingtest.repository.UserTestRepository;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;

    private User user;

    //@Mock
    UserRepository userRepository;

    // @Mock
    UserTestRepository userTestRepository;

    //@Mock
    UserTestDetailRepository userTestDetailRepository;

    QuestionService questionService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userTestRepository = mock(UserTestRepository.class);
        userTestDetailRepository = mock(UserTestDetailRepository.class);
        questionService = mock(QuestionService.class);

        userService = new UserService();
        userService.setUserRepository(userRepository);
        userService.setUserTestRepository(userTestRepository);
        userService.setUserTestDetailRepository(userTestDetailRepository);
        userService.setQuestionService(questionService);


        when(questionService.getQuestionsByTestId(BigInteger.valueOf(1))).thenReturn(Arrays.asList(
                Question.builder()
                        .id(BigInteger.valueOf(1L))
                        .testId(BigInteger.valueOf(1))
                        .name("Question1")
                        .build(),
                Question.builder()
                        .id(BigInteger.valueOf(2L))
                        .testId(BigInteger.valueOf(1))
                        .name("Question2")
                        .build(),
                Question.builder()
                        .id(BigInteger.valueOf(3L))
                        .testId(BigInteger.valueOf(1))
                        .name("Question3")
                        .build()
        ));
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    public void startUserTestWhenAllQuestionsAnswered() {

        when(userTestRepository.save(any())).thenReturn(UserTest.builder()
                .id(BigInteger.valueOf(1))
                .testId(BigInteger.valueOf(1))
                .userId(BigInteger.valueOf(1))
                .started(new Timestamp(System.currentTimeMillis()))
                .numberCorrectQuestions(0)
                .userTestDetails(Arrays.asList(
                        UserTestDetail.builder()
                                .id(BigInteger.valueOf(1L))
                                .userTestId(BigInteger.valueOf(1))
                                .questionId(BigInteger.valueOf(1L))
                                .answerId(BigInteger.valueOf(1L))
                                .build(),
                        UserTestDetail.builder()
                                .id(BigInteger.valueOf(2L))
                                .userTestId(BigInteger.valueOf(1))
                                .questionId(BigInteger.valueOf(2L))
                                .answerId(BigInteger.valueOf(2L))
                                .build(),
                        UserTestDetail.builder()
                                .id(BigInteger.valueOf(3L))
                                .userTestId(BigInteger.valueOf(1))
                                .questionId(BigInteger.valueOf(3L))
                                .answerId(BigInteger.valueOf(3L))
                                .build()
                ))
                .build()
        );

        userService.startUserTest(BigInteger.valueOf(1), BigInteger.valueOf(2));
        Map<UserTest, ArrayDeque<Question>> testQuestionsActual = userService.getTestQuestions();
        assertNotNull(testQuestionsActual);
        assertFalse(testQuestionsActual.isEmpty());
        assertTrue(testQuestionsActual.get(UserTest.builder()
                        .id(BigInteger.valueOf(1))
                        .build())
                .isEmpty());
    }

    @Test
    public void startUserTestWhenNotAllQuestionsAnswered() {
        when(userTestRepository.save(any())).thenReturn(UserTest.builder()
                .id(BigInteger.valueOf(1))
                .testId(BigInteger.valueOf(1))
                .userId(BigInteger.valueOf(1))
                .started(new Timestamp(System.currentTimeMillis()))
                .numberCorrectQuestions(0)
                .userTestDetails(Arrays.asList(
                        UserTestDetail.builder()
                                .id(BigInteger.valueOf(1L))
                                .userTestId(BigInteger.valueOf(1))
                                .questionId(BigInteger.valueOf(1L))
                                .answerId(BigInteger.valueOf(1L))
                                .build(),
                        UserTestDetail.builder()
                                .id(BigInteger.valueOf(2L))
                                .userTestId(BigInteger.valueOf(1))
                                .questionId(BigInteger.valueOf(2L))
                                .answerId(BigInteger.valueOf(2L))
                                .build()
                ))
                .build()
        );

        userService.startUserTest(BigInteger.valueOf(1), BigInteger.valueOf(2));
        Map<UserTest, ArrayDeque<Question>> testQuestionsActual = userService.getTestQuestions();
        assertNotNull(testQuestionsActual);
        assertFalse(testQuestionsActual.isEmpty());
        ArrayDeque<Question> queueQuestionsActual = testQuestionsActual.get(UserTest.builder().id(BigInteger.valueOf(1)).build());
        assertTrue(queueQuestionsActual.size() == 1);

        Question questActual = queueQuestionsActual.peek();

        assertEquals(BigInteger.valueOf(3L), questActual.getId());
        assertEquals(BigInteger.valueOf(1L), questActual.getTestId());
        assertEquals("Question3", questActual.getName());
    }
}
