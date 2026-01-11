package ru.otus.hw.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoIntegrationTest {

    @Mock
    private TestFileNameProvider fileNameProvider;

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
