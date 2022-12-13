package com.passingtest.mapper;

import com.passingtest.dto.QuestionDTO;
import com.passingtest.dto.UserTestDTO;
import com.passingtest.model.entity.Question;
import com.passingtest.model.entity.Test;
import com.passingtest.model.entity.UserTest;

import java.util.stream.Collectors;

public class UserTestMapper {
    public static UserTestDTO toDto(UserTest userTest) {
        return UserTestDTO.builder()
                .id(userTest.getId())
                .testId(userTest.getTestId())
                .testName(userTest.getTest().getName())
                .started(userTest.getStarted().toLocalDateTime())
                .finished(userTest.getFinished() != null ? userTest.getFinished().toLocalDateTime() : null)
                .numberCorrectQuestions(userTest.getNumberCorrectQuestions())
                .numberAllQuestions(userTest.getTest().getQuestions().size())
                .minLevelCorrect(userTest.getTest().getMinLevelCorrect())
                .isTestPassed(userTest.getTestPassed())
                .build();
    }
}
