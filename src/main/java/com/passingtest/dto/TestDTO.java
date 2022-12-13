package com.passingtest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TestDTO {
    private BigInteger id;

    private String name;

    private Short minLevelCorrect;

    private Integer numberAllQuestions;
}
