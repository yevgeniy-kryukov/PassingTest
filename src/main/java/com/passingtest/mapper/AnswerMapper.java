package com.passingtest.mapper;

import com.passingtest.dto.AnswerDTO;
import com.passingtest.model.entity.Answer;

public class AnswerMapper {
    public static AnswerDTO toDto(Answer answer) {
        return new AnswerDTO(answer.getId(), answer.getName(), answer.getImage());
    }
}
