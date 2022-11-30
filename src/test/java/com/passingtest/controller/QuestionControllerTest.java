package com.passingtest.controller;

//import static org.assertj.core.internal.bytebuddy.implementation.FixedValue.value;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.CoreMatchers.

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.passingtest.PassingtestApplication;
import com.passingtest.service.QuestionService;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PassingtestApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class QuestionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    QuestionService questionService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getQuestionById() throws Exception {
        // createTestEmployee("bob");
        mvc.perform(get("/question/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("q1-t1")))
                .andExpect(jsonPath("testId", is(1)))
                .andExpect(jsonPath("image", nullValue(String.class)))
                .andExpect(jsonPath("$.answers", hasSize(2)))
                .andExpect(jsonPath("$.answers[0].id", is(1)))
                .andExpect(jsonPath("$.answers[0].name", is("a1-1-1")))
                .andExpect(jsonPath("$.answers[0].questionId", is(1)))
                .andExpect(jsonPath("$.answers[0].image", nullValue(String.class)))
                .andExpect(jsonPath("$.answers[0].correct", is(true)))
                .andExpect(jsonPath("$.answers[1].id", is(2)))
                .andExpect(jsonPath("$.answers[1].name", is("a1-1-2")))
                .andExpect(jsonPath("$.answers[1].questionId", is(1)))
                .andExpect(jsonPath("$.answers[1].image", nullValue(String.class)))
                .andExpect(jsonPath("$.answers[1].correct", is(false)))
        ;
    }

    @Test
    public void saveQuestion() throws Exception {

    }

    @Test
    public void updateQuestion() {
    }
}
