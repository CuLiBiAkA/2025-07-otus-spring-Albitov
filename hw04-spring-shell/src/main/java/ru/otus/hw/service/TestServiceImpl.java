package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLineLocalized("TestService.answer.the.questions");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            presentQuestion(question);
            var choice = askUserChoice(question);
            var isCorrect = evaluateAnswer(question, choice);

            testResult.applyAnswer(question, isCorrect);
        }
        return testResult;
    }

    private void presentQuestion(Question question) {
        ioService.printLine(question.text());
        for (int i = 0; i < question.answers().size(); i++) {
            var answer = question.answers().get(i);
            ioService.printFormattedLine("%d) %s", i + 1, answer.text());
        }
    }

    private int askUserChoice(Question question) {
        return ioService.readIntForRangeWithPromptLocalized(
                1,
                question.answers().size(),
                "TestService.answer.the.in",
                "TestService.answer.the.error"
        );
    }

    private boolean evaluateAnswer(Question question, int choice) {
        var chosenAnswer = question.answers().get(choice - 1);
        return chosenAnswer.isCorrect();
    }
}
