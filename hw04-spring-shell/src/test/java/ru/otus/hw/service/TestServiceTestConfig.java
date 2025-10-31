package ru.otus.hw.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import ru.otus.hw.dao.QuestionDao;

@TestConfiguration
public class TestServiceTestConfig {

    @MockBean
    private QuestionDao questionDao;

    @MockBean
    private LocalizedIOService ioService;

    @Bean
    public TestService testService(LocalizedIOService ioService, QuestionDao questionDao) {
        return new TestServiceImpl(ioService, questionDao);
    }
}
