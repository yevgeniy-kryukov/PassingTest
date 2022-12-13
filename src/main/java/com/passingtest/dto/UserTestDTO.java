package com.passingtest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.passingtest.model.entity.UserTestDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserTestDTO {
    private BigInteger id;

    private BigInteger testId;

    private String testName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime started;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finished;

    private Integer numberCorrectQuestions;

    private Integer numberAllQuestions;

    private Short minLevelCorrect;

    private boolean isTestPassed;
}
