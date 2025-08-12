package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import java.util.List;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.startsWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TestServiceImplTest {

    private IOService ioService;
    private QuestionDao questionDao;
    private TestServiceImpl testService;

    @BeforeEach
    void setUp() {
        ioService = mock(IOService.class);
        questionDao = mock(QuestionDao.class);
        testService = new TestServiceImpl(ioService, questionDao);
    }

    @Test
    void executeTest_ShouldPrintAllQuestionsAndAnswers() {
        var answers = List.of(
                new Answer("Answer 1", false),
                new Answer("Answer 2", true)
        );

        var questions = List.of(
                new Question("Question 1", answers),
                new Question("Question 2", answers)
        );

        when(questionDao.findAll()).thenReturn(questions);

        testService.executeTest();

        verify(ioService, atLeastOnce()).printFormattedLine("Please answer the questions below%n");
        verify(ioService, times(2)).printLine(startsWith("Question:"));
        verify(ioService, atLeast(4)).printFormattedLine(anyString(), anyInt(), anyString());
    }
}