package com.passingtest.model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "main", name = "test")
public class Test {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "idSeqTest", sequenceName = "main.test_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqTest")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "min_level_correct")
    private Short minLevelCorrect;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private List<Question> questions;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private List<UserTest> userTests;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getMinLevelCorrect() {
        return minLevelCorrect;
    }

    public void setMinLevelCorrect(Short minLevelCorrect) {
        this.minLevelCorrect = minLevelCorrect;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<UserTest> getUserTests() {
        return userTests;
    }

    public void setUserTests(List<UserTest> userTests) {
        this.userTests = userTests;
    }
}
