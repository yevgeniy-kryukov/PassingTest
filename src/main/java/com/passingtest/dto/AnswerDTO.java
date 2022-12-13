package com.passingtest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.math.BigInteger;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerDTO {
    private BigInteger id;

    private String name;

    @Type(type = "org.hibernate.type.BinaryType")
    private Byte[] image;

}
