package com.passingtest.model.entity;

import javax.persistence.*;
import java.math.BigInteger;

//mark class as an Entity
@Entity
//defining class name as Table name
@Table(schema = "main", name = "answer")
public class Answer {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "idSeqAnswer", sequenceName = "main.answer_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqAnswer")
    private BigInteger id;
    @Column(name = "name")
    private String name;
    @Column(name = "is_correct")
    private Boolean isCorrect;
    @Column(name = "question_id")
    private BigInteger questionId;
    @Column(name = "image")
    @Lob
    private Byte[] image;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public BigInteger getQuestionId() {
        return questionId;
    }

    public void setQuestionId(BigInteger questionId) {
        this.questionId = questionId;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }
}
