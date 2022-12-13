package com.passingtest.mapper;

import com.passingtest.dto.TestDTO;
import com.passingtest.model.entity.Test;

public class TestMapper {
    public static TestDTO toDto(Test test) {
        return new TestDTO(test.getId(), test.getName(), test.getMinLevelCorrect(), test.getQuestions().size());
    }
}
