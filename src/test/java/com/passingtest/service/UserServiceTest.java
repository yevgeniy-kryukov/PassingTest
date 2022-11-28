package com.passingtest.service;

import static org.junit.jupiter.api.Assertions.*;

import com.passingtest.model.entity.*;
import com.passingtest.repository.UserRepository;
import com.passingtest.repository.UserTestDetailRepository;
import com.passingtest.repository.UserTestRepository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;

    //@Mock
    UserRepository userRepository;

    // @Mock
    UserTestRepository userTestRepository;

    //@Mock
    UserTestDetailRepository userTestDetailRepository;

    QuestionService questionService;

    AnswerService answerService;

    List<Question> questions = new ArrayList<>();

    List<Answer> answers = new ArrayList<>();

    UserTest userTest;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userTestRepository = mock(UserTestRepository.class);
        userTestDetailRepository = mock(UserTestDetailRepository.class);
        questionService = mock(QuestionService.class);
        answerService = mock(AnswerService.class);

        userService = new UserService();
        userService.setUserRepository(userRepository);
        userService.setUserTestRepository(userTestRepository);
        userService.setUserTestDetailRepository(userTestDetailRepository);
        userService.setQuestionService(questionService);
        userService.setAnswerService(answerService);

        questions.add(Question.builder()
                .id(BigInteger.valueOf(1L))
                .testId(BigInteger.valueOf(1))
                .name("Question1")
                .build());
        questions.add(Question.builder()
                .id(BigInteger.valueOf(2L))
                .testId(BigInteger.valueOf(1))
                .name("Question2")
                .build());
        questions.add(Question.builder()
                .id(BigInteger.valueOf(3L))
                .testId(BigInteger.valueOf(1))
                .name("Question3")
                .build());

        when(questionService.getQuestionsByTestId(BigInteger.valueOf(1))).thenReturn(questions);

        List<UserTestDetail> userTestDetails = new ArrayList<>();
        userTestDetails.add(UserTestDetail.builder()
                .id(BigInteger.valueOf(1L))
                .userTestId(BigInteger.valueOf(1))
                .questionId(BigInteger.valueOf(1L))
                .answerId(BigInteger.valueOf(1L))
                .build());
        userTestDetails.add(UserTestDetail.builder()
                .id(BigInteger.valueOf(2L))
                .userTestId(BigInteger.valueOf(1))
                .questionId(BigInteger.valueOf(2L))
                .answerId(BigInteger.valueOf(2L))
                .build());
        userTestDetails.add(UserTestDetail.builder()
                .id(BigInteger.valueOf(3L))
                .userTestId(BigInteger.valueOf(1))
                .questionId(BigInteger.valueOf(3L))
                .answerId(BigInteger.valueOf(3L))
                .build());

        userTest = UserTest.builder()
                .id(BigInteger.valueOf(1))
                .testId(BigInteger.valueOf(1))
                .userId(BigInteger.valueOf(1))
                .started(new Timestamp(System.currentTimeMillis()))
                .numberCorrectQuestions(0)
                .userTestDetails(userTestDetails)
                .build();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    public void startUserTestWhenAllQuestionsAnswered() {
        when(userTestRepository.save(any())).thenReturn(userTest);
        userService.startUserTest(BigInteger.valueOf(1), BigInteger.valueOf(1));
        Map<UserTest, ArrayDeque<Question>> testQuestionsActual = userService.getTestQuestions();
        assertNotNull(testQuestionsActual);
        assertFalse(testQuestionsActual.isEmpty());
        assertTrue(testQuestionsActual.get(userTest).isEmpty());
    }

    @Test
    public void startUserTestWhenNotAllQuestionsAnswered() {
        userTest.getUserTestDetails().remove(2);

        when(userTestRepository.save(any())).thenReturn(userTest);

        userService.startUserTest(BigInteger.valueOf(1), BigInteger.valueOf(1));
        Map<UserTest, ArrayDeque<Question>> testQuestionsActual = userService.getTestQuestions();
        assertNotNull(testQuestionsActual);
        assertFalse(testQuestionsActual.isEmpty());
        ArrayDeque<Question> queueQuestionsActual = testQuestionsActual.get(userTest);
        assertEquals(1, queueQuestionsActual.size());

        Question questActual = queueQuestionsActual.peekFirst();

        assertEquals(BigInteger.valueOf(3L), questActual.getId());
        assertEquals(BigInteger.valueOf(1L), questActual.getTestId());
        assertEquals("Question3", questActual.getName());
    }

    @Test
    public void getNextQuestionWithoutStartUserTest() {
        Question question = userService.getNextQuestion(userTest);
        assertNull(question);
    }

    @Test
    public void getNextQuestionWithStartUserTest() {
        userTest.getUserTestDetails().remove(2);
        userTest.getUserTestDetails().remove(1);

        when(userTestRepository.save(any())).thenReturn(userTest);
        when(answerService.isSelectedQuestionAnswersIsCorrect(any(), anyList())).thenReturn(true);
        UserTest userTest = userService.startUserTest(BigInteger.valueOf(1), BigInteger.valueOf(1));

        Question question;
        int i = 1;
        while ((question = userService.getNextQuestion(userTest)) != null) {
            i++;
            assertNotNull(question);
            assertEquals(userTest.getTestId(), question.getTestId());
            assertEquals("Question" + i, question.getName());
            assertEquals(userService.getTestQuestions().get(userTest).size(), questions.size() + 1 - i);

            userService.saveAnswers(userTest, question,
                    Arrays.asList(Answer.builder()
                                    .id(BigInteger.valueOf(1))
                                    .questionId(BigInteger.valueOf(1))
                                    .name("Answer1")
                                    .build(),
                            Answer.builder()
                                    .id(BigInteger.valueOf(1))
                                    .questionId(BigInteger.valueOf(1))
                                    .name("Answer2")
                                    .build()
                    ));
        }

    }

    @Test
    public void saveAnswersWithCorrectAnswersTest() {
        questions.add(Question.builder()
                .id(BigInteger.valueOf(4L))
                .name("Question4")
                .testId(BigInteger.valueOf(1L))
                .build());

        answers.addAll(Arrays.asList(
                Answer.builder()
                        .id(BigInteger.valueOf(1L))
                        .name("Answer1")
                        .isCorrect(true)
                        .questionId(BigInteger.valueOf(4L))
                        .build(),
                Answer.builder()
                        .id(BigInteger.valueOf(2L))
                        .name("Answer2")
                        .isCorrect(false)
                        .questionId(BigInteger.valueOf(4L))
                        .build(),
                Answer.builder()
                        .id(BigInteger.valueOf(3L))
                        .name("Answer3")
                        .isCorrect(true)
                        .questionId(BigInteger.valueOf(4L))
                        .build()
        ));

        when(questionService.getQuestionsByTestId(BigInteger.valueOf(1))).thenReturn(questions);
        when(userTestRepository.save(any())).thenReturn(userTest);
        when(answerService.isSelectedQuestionAnswersIsCorrect(questions.get(3),
                Arrays.asList(answers.get(0), answers.get(2)))).thenReturn(true);

        userService.startUserTest(userTest.getUserId(), userTest.getTestId());

        Question question = userService.getNextQuestion(userTest);

        assertEquals(1, userService.getTestQuestions().get(userTest).size());

        userService.saveAnswers(userTest, question, Arrays.asList(answers.get(0), answers.get(2)));

        assertEquals(1, userService.getNumberCorrectQuestions().get(userTest));
        assertEquals(1, userTest.getNumberCorrectQuestions());

        assertTrue(userService.getTestQuestions().get(userTest).isEmpty());

        verify(userTestDetailRepository, times(2)).save(any(UserTestDetail.class));
    }

    @Test
    public void saveAnswersWithNotCorrectAnswersTest() {
        questions.add(Question.builder()
                .id(BigInteger.valueOf(4L))
                .name("Question4")
                .testId(BigInteger.valueOf(1L))
                .build());

        answers.addAll(Arrays.asList(
                Answer.builder()
                        .id(BigInteger.valueOf(1L))
                        .name("Answer1")
                        .isCorrect(true)
                        .questionId(BigInteger.valueOf(4L))
                        .build(),
                Answer.builder()
                        .id(BigInteger.valueOf(2L))
                        .name("Answer2")
                        .isCorrect(false)
                        .questionId(BigInteger.valueOf(4L))
                        .build(),
                Answer.builder()
                        .id(BigInteger.valueOf(3L))
                        .name("Answer3")
                        .isCorrect(true)
                        .questionId(BigInteger.valueOf(4L))
                        .build()
        ));

        when(questionService.getQuestionsByTestId(BigInteger.valueOf(1))).thenReturn(questions);
        when(userTestRepository.save(any())).thenReturn(userTest);
        when(answerService.isSelectedQuestionAnswersIsCorrect(questions.get(3),
                Arrays.asList(answers.get(0), answers.get(2)))).thenReturn(true);

        userService.startUserTest(userTest.getUserId(), userTest.getTestId());

        Question question = userService.getNextQuestion(userTest);

        assertEquals(1, userService.getTestQuestions().get(userTest).size());

        userService.saveAnswers(userTest, question, Arrays.asList(answers.get(0), answers.get(1)));

        assertEquals(0, userService.getNumberCorrectQuestions().get(userTest));
        assertEquals(0, userTest.getNumberCorrectQuestions());

        assertTrue(userService.getTestQuestions().get(userTest).isEmpty());

        verify(userTestDetailRepository, times(2)).save(any(UserTestDetail.class));
    }

    @Test
    public void saveAnswersExceptionWhenQuestionsNotFoundTest() {
        answers.addAll(Arrays.asList(
                Answer.builder()
                        .id(BigInteger.valueOf(1L))
                        .name("Answer1")
                        .isCorrect(true)
                        .questionId(questions.get(2).getId())
                        .build(),
                Answer.builder()
                        .id(BigInteger.valueOf(2L))
                        .name("Answer2")
                        .isCorrect(false)
                        .questionId(questions.get(2).getId())
                        .build(),
                Answer.builder()
                        .id(BigInteger.valueOf(3L))
                        .name("Answer3")
                        .isCorrect(true)
                        .questionId(questions.get(2).getId())
                        .build()
        ));

        when(userTestRepository.save(any())).thenReturn(userTest);
        when(questionService.getQuestionsByTestId(BigInteger.valueOf(1))).thenReturn(questions);
        when(answerService.isSelectedQuestionAnswersIsCorrect(questions.get(2),
                Arrays.asList(answers.get(0), answers.get(2)))).thenReturn(true);

        userService.startUserTest(userTest.getUserId(), userTest.getTestId());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.saveAnswers(userTest, questions.get(2), Arrays.asList(answers.get(0), answers.get(1)));
        }, "Ожидалось исключение RuntimeException");

        Assertions.assertEquals("Вопросы теста не найдены!", thrown.getMessage());
    }

    @Test
    public void saveAnswersExceptionWhenQuestionNotContainsTest() {
        questions.add(Question.builder()
                .id(BigInteger.valueOf(4L))
                .name("Question4")
                .testId(BigInteger.valueOf(1L))
                .build());

        Question question = Question.builder()
                .id(BigInteger.valueOf(5L))
                .name("Question5")
                .testId(BigInteger.valueOf(1L))
                .build();

        answers.addAll(Arrays.asList(
                Answer.builder()
                        .id(BigInteger.valueOf(1L))
                        .name("Answer1")
                        .isCorrect(true)
                        .questionId(BigInteger.valueOf(5L))
                        .build(),
                Answer.builder()
                        .id(BigInteger.valueOf(2L))
                        .name("Answer2")
                        .isCorrect(false)
                        .questionId(BigInteger.valueOf(5L))
                        .build(),
                Answer.builder()
                        .id(BigInteger.valueOf(3L))
                        .name("Answer3")
                        .isCorrect(true)
                        .questionId(BigInteger.valueOf(5L))
                        .build()
        ));

        when(userTestRepository.save(any())).thenReturn(userTest);
        when(questionService.getQuestionsByTestId(BigInteger.valueOf(1))).thenReturn(questions);
        when(answerService.isSelectedQuestionAnswersIsCorrect(question,
                Arrays.asList(answers.get(0), answers.get(2)))).thenReturn(true);

        userService.startUserTest(userTest.getUserId(), userTest.getTestId());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.saveAnswers(userTest, question, Arrays.asList(answers.get(0), answers.get(1)));
        }, "Ожидалось исключение RuntimeException");

        Assertions.assertEquals("Вопрос не относится к данному тесту!", thrown.getMessage());
    }

    @Test
    public void continueUserTestExceptionWhenFinished() {
        userTest.setFinished(new Timestamp(System.currentTimeMillis()));
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.continueUserTest(userTest);
        }, "Ожидалось исключение RuntimeException");

        Assertions.assertEquals("Тест был завершен, прохождение теста невозможно!", thrown.getMessage());
    }

    @Test
    public void continueUserTestNumberCorrectQuestions() {
        userTest.setNumberCorrectQuestions(1);
        userService.continueUserTest(userTest);
        assertEquals(userTest.getNumberCorrectQuestions(), userService.getNumberCorrectQuestions().get(userTest));
    }

    @Test
    public void continueUserTestSetQuestions() {
        questions.add(Question.builder()
                .id(BigInteger.valueOf(4L))
                .name("Question4")
                .testId(BigInteger.valueOf(1L))
                .build());
        questions.add(Question.builder()
                .id(BigInteger.valueOf(5L))
                .name("Question5")
                .testId(BigInteger.valueOf(1L))
                .build());
        when(questionService.getQuestionsByTestId(BigInteger.valueOf(1))).thenReturn(questions);
        userService.continueUserTest(userTest);
        assertEquals(2, userService.getTestQuestions().get(userTest).size());
    }
}
