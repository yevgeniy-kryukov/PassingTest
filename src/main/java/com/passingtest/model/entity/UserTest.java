package com.passingtest.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "main", name = "user_test")
public class UserTest {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "idSeqUserTest", sequenceName = "main.user_test_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqUserTest")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "test_id")
    private Integer testId;
    @Column(name = "started")
    private Timestamp started;
    @Column(name = "finished")
    private Timestamp finished;
    @Column(name = "number_correct_questions")
    private Integer numberCorrectQuestions;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_test_id")
    private List<UserTestDetail> userTestDetails;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }

    public Timestamp getStarted() {
        return started;
    }

    public void setStarted(Timestamp started) {
        this.started = started;
    }

    public Timestamp getFinished() {
        return finished;
    }

    public void setFinished(Timestamp finished) {
        this.finished = finished;
    }

    public Integer getNumberCorrectQuestions() {
        return numberCorrectQuestions;
    }

    public void setNumberCorrectQuestions(Integer numberCorrectQuestions) {
        this.numberCorrectQuestions = numberCorrectQuestions;
    }

    public List<UserTestDetail> getUserTestDetails() {
        return userTestDetails;
    }

    public void setUserTestDetails(List<UserTestDetail> userTestDetails) {
        this.userTestDetails = userTestDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTest)) return false;
        UserTest userTest = (UserTest) o;
        return id.equals(userTest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
