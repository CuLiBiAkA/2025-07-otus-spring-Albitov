package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TestServiceImplTest {

    @Test
    void shouldAskAllQuestionsAndReturnResult() {
        IOService io = mock(IOService.class);
        QuestionDao dao = mock(QuestionDao.class);

        when(dao.findAll()).thenReturn(List.of(
                new Question("Q1", List.of(new Answer("A1", false), new Answer("A2", true))),
                new Question("Q2", List.of(new Answer("B1", true)))
        ));

        when(io.readIntForRangeWithPrompt(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(1);

        var service = new TestServiceImpl(io, dao);
        var student = new Student("Ivan", "Petrov");

        TestResult result = service.executeTestFor(student);

        assertThat(result.getAnsweredQuestions()).hasSize(2);
    }
}
