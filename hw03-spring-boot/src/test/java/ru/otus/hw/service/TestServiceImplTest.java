package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestServiceImplTest {

    @Mock
    private LocalizedIOService io;

    @Mock
    private QuestionDao dao;

    @InjectMocks
    private TestServiceImpl testService;

    @Test
    void shouldAskAllQuestionsAndReturnResult() {
        when(dao.findAll()).thenReturn(List.of(
                new Question("Q1", List.of(new Answer("A1", false), new Answer("A2", true))),
                new Question("Q2", List.of(new Answer("B1", true)))
        ));

        when(io.readIntForRangeWithPromptLocalized(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(1);


        var student = new Student("Ivan", "Petrov");

        TestResult result = testService.executeTestFor(student);

        assertThat(result.getAnsweredQuestions()).hasSize(2);
    }
}
