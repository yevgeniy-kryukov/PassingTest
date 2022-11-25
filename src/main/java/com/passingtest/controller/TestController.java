package com.passingtest.controller;

import com.passingtest.exception.ObjectNotFoundException;
import com.passingtest.model.entity.Test;
import com.passingtest.service.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

//mark class as Controller
@RestController
public class TestController {
    //autowire the BooksService class
    @Autowired
    TestService testService;

    //creating a get mapping that retrieves all the books detail from the database
    @GetMapping("/tests")
    private List<Test> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("/test/{id}")
    private Test getTestById(@PathVariable("id") int id) {
        return testService.getTestById(id);
    }

    @PostMapping("saveTest")
    public Test saveTest(@RequestBody Test test) {
        testService.saveOrUpdate(test);
        return test;
    }

    @PutMapping("updateTest/{id}")
    public Test updateTest(@RequestBody Test test, @PathVariable("id") Integer id) {
        test.setId(id);
        testService.saveOrUpdate(test);
        return test;
    }

}
