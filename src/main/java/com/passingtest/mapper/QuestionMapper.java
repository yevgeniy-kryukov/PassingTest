package com.passingtest.mapper;

import com.passingtest.dto.AnswerDTO;
import com.passingtest.dto.QuestionDTO;
import com.passingtest.model.entity.Answer;
import com.passingtest.model.entity.Question;

import java.util.stream.Collectors;

public class QuestionMapper {
    public static QuestionDTO toDto(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .name(question.getName())
                .image(question.getImage())
                .answers(question.getAnswers().stream().map(AnswerMapper::toDto).collect(Collectors.toList()))
                .build();
    }
}
