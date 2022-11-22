package com.passingtest.service;

import com.passingtest.model.entity.Test;
import com.passingtest.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    @Autowired
    TestRepository testRepository;

    public List<Test> getAllTests() {
        List<Test> tests = new ArrayList<Test>();
        testRepository.findAll().forEach(test1 -> tests.add(test1));
        return tests;
    }

    public Test getTestById(int id) {
        return testRepository.findById(id).get();
    }

    public void saveOrUpdate(Test test) {
        testRepository.save(test);
    }
}
