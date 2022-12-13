package com.passingtest.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(schema = "main", name = "test")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "idSeqTest", sequenceName = "main.test_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqTest")
    private BigInteger id;
    @Column(name = "name")
    private String name;
    @Column(name = "min_level_correct")
    private Short minLevelCorrect;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private List<Question> questions;

    public Test(BigInteger id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", minLevelCorrect=" + minLevelCorrect +
                ", questions=" + questions +
                '}';
    }
}
