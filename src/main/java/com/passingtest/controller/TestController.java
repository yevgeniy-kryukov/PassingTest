package com.passingtest.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.passingtest.model.entity.Test;
import com.passingtest.service.TestService;

//mark class as Controller
@RestController
public class TestController {
    //autowire the BooksService class
    @Autowired
    TestService testService;

    @GetMapping("/tests")
    public List<Test> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("/test/{id}")
    public Test getTestById(@PathVariable("id") int id) {
        return testService.getTestById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/saveTest")
    public Test saveTest(@RequestBody Test test) {
        testService.saveOrUpdate(test);
        return test;
    }
    @PutMapping("/updateTest/{id}")
    public Test updateTest(@RequestBody Test test, @PathVariable("id") int id) {
        test.setId(BigInteger.valueOf(id));
        testService.saveOrUpdate(test);
        return test;
    }

}
