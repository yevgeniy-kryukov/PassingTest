package com.passingtest.service;

import com.passingtest.model.entity.Question;
import com.passingtest.model.entity.Test;
import com.passingtest.model.entity.User;
import com.passingtest.model.entity.UserTest;
import com.passingtest.repository.UserRepository;
import com.passingtest.repository.UserTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTestRepository userTestRepository;

    private ArrayDeque<Question> currentUserTestQuestions;

    private Integer currentUserTestNumberCorrectQuestions;

    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    public void userSaveOrUpdate(User user) {
        userRepository.save(user);
    }

    public List<UserTest> getUserTestsByUserId(Integer userId) {
        List<UserTest> userTests = new ArrayList<UserTest>();
        userTestRepository.findByUserId(userId).forEach((userTest) -> userTests.add(userTest));
        return userTests;
    }

    public UserTest startUserTest(Test test) {
        return null;
    }

    public boolean updateUserTest(UserTest userTest) {
        return false;
    }

    public boolean finishUserTest(UserTest userTest) {
        return false;
    }

    private boolean fillCurrentUserTestQuestions() {
        return false;
    }

    private Question getNextQuestionFromCurrentUserTestQuestions() {
        return null;
    }

    public boolean userAuthentication(String userName, String pwd) {
        return false;
    }
}
