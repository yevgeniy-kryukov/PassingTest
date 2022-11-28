package com.passingtest.model.entity;

import javax.persistence.*;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "main", name = "user_test_detail")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTestDetail {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "idSeqUserTestDetail", sequenceName = "main.user_test_detail_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqUserTestDetail")
    private BigInteger id;
    @Column(name = "user_test_id", nullable = false)
    private BigInteger userTestId;
    @Column(name = "question_id", nullable = false)
    private BigInteger questionId;
    @Column(name = "answer_id", nullable = false)
    private BigInteger answerId;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getUserTestId() {
        return userTestId;
    }

    public void setUserTestId(BigInteger userTestId) {
        this.userTestId = userTestId;
    }

    public BigInteger getQuestionId() {
        return questionId;
    }

    public void setQuestionId(BigInteger questionId) {
        this.questionId = questionId;
    }

    public BigInteger getAnswerId() {
        return answerId;
    }

    public void setAnswerId(BigInteger answerId) {
        this.answerId = answerId;
    }
}
