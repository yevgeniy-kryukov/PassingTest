package com.passingtest.model.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "main", name = "user_test")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTest {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @SequenceGenerator(name = "idSeqUserTest", sequenceName = "main.user_test_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSeqUserTest")
    private BigInteger id;
    @Column(name = "user_id", nullable = false)
    private BigInteger userId;
    @Column(name = "test_id", nullable = false)
    private BigInteger testId;
    @Column(name = "started", nullable = false)
    private Timestamp started;
    @Column(name = "finished")
    private Timestamp finished;
    @Column(name = "number_correct_questions", nullable = false)
    private Integer numberCorrectQuestions;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_test_id")
    private List<UserTestDetail> userTestDetails;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private Test test;

    @Column(name = "is_test_passed", nullable = false)
    private Boolean isTestPassed = false;

    public Boolean getTestPassed() {
        return isTestPassed;
    }

    public void setTestPassed(Boolean testPassed) {
        isTestPassed = testPassed;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getTestId() {
        return testId;
    }

    public void setTestId(BigInteger testId) {
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

    @Override
    public String toString() {
        return "UserTest{" +
                "id=" + id +
                ", userId=" + userId +
                ", testId=" + testId +
                ", started=" + started +
                ", finished=" + finished +
                ", numberCorrectQuestions=" + numberCorrectQuestions +
                ", userTestDetails=" + userTestDetails +
                ", test=" + test +
                ", isTestPassed=" + isTestPassed +
                '}';
    }
}
