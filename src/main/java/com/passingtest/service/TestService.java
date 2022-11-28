package com.passingtest.service;

import com.passingtest.exception.ObjectNotFoundException;
import com.passingtest.model.entity.Test;
import com.passingtest.repository.TestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TestService {
    @Autowired
    TestRepository testRepository;

    public List<Test> getAllTests() {
        List<Test> tests = new ArrayList<Test>();
        /* for example todo remove later
         testRepository.findAll().forEach(tests::add);
         return tests;
         */
        return StreamSupport.stream(testRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Test getTestById(BigInteger id) throws ObjectNotFoundException {
        return testRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Test.class));
    }

    public void saveOrUpdate(Test test) {
        testRepository.save(test);
    }

    public void delete(int id) {
        testRepository.delete(new Test(BigInteger.valueOf(id)));
    }
}
