package com.passingtest.controller;

import com.passingtest.model.entity.Test;
import com.passingtest.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
