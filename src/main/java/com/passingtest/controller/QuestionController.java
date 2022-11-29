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

import com.passingtest.model.entity.Question;
import com.passingtest.service.QuestionService;

@RestController
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public Question getQuestionById(@PathVariable("id") BigInteger id) {
        return questionService.getQuestionById(id);

    }

    @GetMapping("/questionsByTestId/{testId}")
    public List<Question> getQuestionsByTestId(@PathVariable("testId") BigInteger testId) {
        return questionService.getQuestionsByTestId(testId);
    }

    @PostMapping("/saveQuestion")
    @ResponseStatus(HttpStatus.CREATED)
    public Question saveQuestion(@RequestBody Question question) {
        return saveOrUpdateQuestion(question);
    }

    @PutMapping("/updateQuestion")
    public Question updateQuestion(@RequestBody Question question) {
        return saveOrUpdateQuestion(question);
    }

    private Question saveOrUpdateQuestion(Question question) {
        questionService.saveOrUpdate(question);
        return question;
    }
}
