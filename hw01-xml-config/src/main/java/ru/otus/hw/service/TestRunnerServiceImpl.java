
package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final IOService ioService;

    @Override
    public void run() {
        try {
            testService.executeTest();
        } catch (Exception ex) {
            ioService.printLine("An unforeseen mistake occurred. We are already working on this.");
        }
    }
}
