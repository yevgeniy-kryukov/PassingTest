package com.passingtest.service;

import com.passingtest.model.entity.User;
import com.passingtest.model.entity.UserTest;
import com.passingtest.repository.UserRepository;
import org.junit.jupiter.api.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserServiceTest {

    private UserService userService;

    private User user;

    @BeforeEach
    void setUp()  {
        userService = new UserService();

        user = new User();
        user.setName("User1");
        user.setPwd("123456");

        userService.userSaveOrUpdate(user);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    public void startUserTest() {
//        userService.startUserTest(user.getId(), )
//
//        UserTest userTest = new UserTest();
//        userTest.setTestId(testId);
//        userTest.setStarted(new Timestamp(System.currentTimeMillis()));
//        userTest.setUserId(userId);
//        userTest.setNumberCorrectQuestions(0);
//        userTestRepository.save(userTest);
//        setQuestions(userTest);
//        return userTest;
    }
}
