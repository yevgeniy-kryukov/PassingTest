package com.passingtest.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import com.passingtest.dto.TestDTO;
import com.passingtest.mapper.TestMapper;
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

    @Autowired
    TestService testService;

    @GetMapping("/tests")
    public List<TestDTO> getAllTests() {
        return testService.getAllTests().stream().map(TestMapper::toDto).filter(el->el.getNumberAllQuestions()>0).collect(Collectors.toList());
    }

    @GetMapping("/test/{id}")
    public Test getTestById(@PathVariable("id") BigInteger id) {
        return testService.getTestById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/saveTest")
    public Test saveTest(@RequestBody Test test) {
        return saveOrUpdate(test);
    }

    @PutMapping("/updateTest")
    public Test updateTest(@RequestBody Test test) {
        return saveOrUpdate(test);
    }

    private Test saveOrUpdate(Test test) {
        testService.saveOrUpdate(test);
        return test;
    }
}
