package com.passingtest.dto;

import com.passingtest.model.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.math.BigInteger;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionDTO {
    private BigInteger id;

    private String name;

    @Type(type = "org.hibernate.type.BinaryType")
    private Byte[] image;

    private List<AnswerDTO> answers;
}
