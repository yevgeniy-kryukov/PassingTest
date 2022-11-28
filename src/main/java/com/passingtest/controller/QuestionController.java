package com.passingtest.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.passingtest.model.entity.Question;
import com.passingtest.service.QuestionService;

@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public Question getQuestionById(@PathVariable("id") int id) {
        return questionService.getQuestionById(BigInteger.valueOf(id));
    }

    @PostMapping("/saveQuestion")
    @ResponseStatus(HttpStatus.CREATED)
    public Question saveQuestion(@RequestBody Question question) {
        questionService.saveOrUpdate(question);
        return question;
    }

    @PutMapping("/updateQuestion/{id}")
    public Question updateQuestion(@RequestBody Question question, @PathVariable("id") int id) {
        question.setId(BigInteger.valueOf(id));
        questionService.saveOrUpdate(question);
        return question;
    }
}
