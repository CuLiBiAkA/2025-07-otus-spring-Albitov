package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {TestServiceImpl.class})
@DisplayName("TestServiceImpl с контекстом Spring Boot")
class TestServiceImplTest {

    @Autowired
    private TestServiceImpl testService;

    @MockBean
    private LocalizedIOService ioService;

    @MockBean
    private QuestionDao questionDao;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student("Иван", "Иванов");
    }

    @Test
    @DisplayName("должен корректно пройти тест, если студент выбирает правильные ответы")
    void shouldReturnCorrectResultWhenAllAnswersRight() {
        // given
        var q1 = new Question("2+2=?", List.of(
                new Answer("3", false),
                new Answer("4", true)
        ));
        var q2 = new Question("Столица Франции?", List.of(
                new Answer("Париж", true),
                new Answer("Берлин", false)
        ));

        when(questionDao.findAll()).thenReturn(List.of(q1, q2));
        when(ioService.readIntForRangeWithPromptLocalized(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(2, 1);

        TestResult result = testService.executeTestFor(student);

        assertThat(result.getRightAnswersCount()).isEqualTo(2);
        verify(ioService, atLeastOnce()).printFormattedLineLocalized("TestService.answer.the.questions");
        verify(questionDao, times(1)).findAll();
    }

    @Test
    @DisplayName("должен корректно обработать неправильные ответы")
    void shouldReturnResultWithWrongAnswers() {
        var q1 = new Question("2+2=?", List.of(
                new Answer("3", false),
                new Answer("4", true)
        ));

        when(questionDao.findAll()).thenReturn(List.of(q1));
        when(ioService.readIntForRangeWithPromptLocalized(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(1);

        TestResult result = testService.executeTestFor(student);

        assertThat(result.getRightAnswersCount()).isZero();
        verify(questionDao, times(1)).findAll();
    }

    @Test
    @DisplayName("должен выводить вопросы и варианты ответов в консоль")
    void shouldPrintQuestionsAndAnswers() {
        var q = new Question("Что такое Spring?", List.of(
                new Answer("Фреймворк", true),
                new Answer("Цветок", false)
        ));
        when(questionDao.findAll()).thenReturn(List.of(q));
        when(ioService.readIntForRangeWithPromptLocalized(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(1);

        testService.executeTestFor(student);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ioService, atLeastOnce()).printLine(captor.capture());
        assertThat(captor.getAllValues()).contains("Что такое Spring?");
    }
}
