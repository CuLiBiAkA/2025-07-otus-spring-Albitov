package ru.otus.hw.dao;

import org.junit.jupiter.api.Test;
import ru.otus.hw.config.TestFileNameProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CsvQuestionDaoIntegrationTest {

    private final TestFileNameProvider fileNameProvider = mock(TestFileNameProvider.class);

    @Test
    void shouldReadQuestionsFromClasspathCsv() {
        when(fileNameProvider.getTestFileName()).thenReturn("questions.csv");
        QuestionDao dao = new CsvQuestionDao(fileNameProvider);

        var questions = dao.findAll();

        assertThat(questions).isNotEmpty();
        assertThat(questions.get(0).text()).isNotBlank();
        assertThat(questions.get(0).answers()).isNotEmpty();
    }
}
