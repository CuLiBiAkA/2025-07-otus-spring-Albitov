package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    public static final String D_S = "%d. %s";

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");

        var questions = questionDao.findAll();

        questions.forEach(question -> {
            ioService.printLine("Question: " + question.text());
            var answers = question.answers();

            for (int i = 0; i < answers.size(); i++) {
                ioService.printFormattedLine(D_S, i + 1, answers.get(i).text());
            }

            ioService.printLine("");
        });
    }
}