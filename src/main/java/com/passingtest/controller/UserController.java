package com.passingtest.controller;

import com.passingtest.dto.UserDTO;
import com.passingtest.dto.UserTestDTO;
import com.passingtest.exception.AppAuthException;
import com.passingtest.exception.AppNotFoundException;
import com.passingtest.exception.AppValidationException;
import com.passingtest.mapper.QuestionMapper;
import com.passingtest.mapper.UserMapper;
import com.passingtest.mapper.UserTestMapper;
import com.passingtest.model.entity.Question;
import com.passingtest.model.entity.User;
import com.passingtest.model.entity.UserTest;
import com.passingtest.service.AnswerService;
import com.passingtest.service.QuestionService;
import com.passingtest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @GetMapping("/test/list/{userId}")
    public ResponseEntity<List<UserTestDTO>> getUserTestsByUserId(@PathVariable("userId") BigInteger userId) {
        List<UserTest> userTests = userService.getUserTestsByUserId(userId);
        List<UserTestDTO> userTestDTOS = userTests.stream()
                .map(UserTestMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userTestDTOS);
    }

    @PostMapping(path = "/test/start", produces = "application/json", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<UserTestDTO> startUserTest(@RequestParam BigInteger userId, @RequestParam BigInteger testId) {
        UserTest userTest = userService.startUserTest(userId, testId);
        return new ResponseEntity<UserTestDTO>(UserTestMapper.toDto(userTest), HttpStatus.CREATED);
    }

    @PostMapping(path = "/test/continue", produces = "application/json", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<UserTestDTO> continueUserTest(@RequestParam BigInteger userTestId) throws AppValidationException {
        UserTest userTest = userService.getUserTestById(userTestId);
        UserTestDTO userTestDTO = UserTestMapper.toDto(userService.continueUserTest(userTest));
        return ResponseEntity.ok(userTestDTO);
    }

    @GetMapping("/test/nextQuestion/{userTestId}")
    public ResponseEntity<Object> getNextQuestion(@PathVariable("userTestId") BigInteger userTestId) throws AppNotFoundException {
        Question question = userService.getNextQuestion(userService.getUserTestById(userTestId));
        Object responseObject = null;
        if (question != null) {
            responseObject = (Object) QuestionMapper.toDto(question);
        } else {
            responseObject = Collections.singletonMap("message", "Вопросы закончились");
        }
        return ResponseEntity.ok(responseObject);
    }

    @PostMapping(path = "/test/saveAnswers", produces = "application/json", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<Map<String, String>> saveAnswers(@RequestParam BigInteger userTestId,
                                                           @RequestParam BigInteger questionId,
                                                           @RequestParam List<BigInteger> answerIDS) throws AppNotFoundException, AppValidationException {
        userService.saveAnswers(userService.getUserTestById(userTestId),
                questionService.getQuestionById(questionId),
                answerIDS.stream().map(el -> answerService.getAnswerById(el)).collect(Collectors.toList()));
        return ResponseEntity.ok(Collections.singletonMap("message", "Данные сохранены успешно"));
    }

    @PostMapping(path = "/test/finish", produces = "application/json", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<UserTestDTO> finishUserTest(@RequestParam BigInteger userTestId) throws AppValidationException {
        UserTest userTest = userService.finishUserTest(userService.getUserTestById(userTestId));
        return ResponseEntity.ok(UserTestMapper.toDto(userTest));
    }

    @PostMapping(path = "/auth", produces = "application/json", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<UserDTO> userAuthentication(@RequestParam String userName, @RequestParam String pwd) throws AppAuthException {
        User user = userService.userAuthentication(userName, pwd);
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @PostMapping(path = "/logout", produces = "application/json")
    public ResponseEntity<Map<String,String>> userAuthentication(HttpServletRequest request)  {
        HttpSession session=request.getSession();
        session.invalidate();
        return ResponseEntity.ok(Collections.singletonMap("message", "Сессия закрыта"));
    }
}
