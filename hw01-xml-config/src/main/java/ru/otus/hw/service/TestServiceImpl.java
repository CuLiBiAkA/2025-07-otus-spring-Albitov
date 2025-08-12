package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private static final String ANSWER_OPTION_FORMAT = "%d. %s";

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");

        var questions = questionDao.findAll();
        printQuestions(questions);
    }

    private void printQuestions(List<Question> questions) {
        questions.forEach(question -> {
            ioService.printLine("Question: " + question.text());
            var answers = question.answers();

            for (int i = 0; i < answers.size(); i++) {
                ioService.printFormattedLine(ANSWER_OPTION_FORMAT, i + 1, answers.get(i).text());
            }

            ioService.printLine("");
        });
    }
}