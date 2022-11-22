package com.passingtest.model.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

//mark class as an Entity
@Entity
//defining class name as Table name
@Table(schema = "main", name = "question")
public class Question {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "idSeqQuestion", sequenceName = "main.question_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqQuestion")
    private BigInteger id;
    @Column(name = "name")
    private String name;
    @Column(name = "test_id")
    private Integer testId;
    @Column(name = "image")
    @Lob
    private Byte[] image;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private List<Answer> answers;

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

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
