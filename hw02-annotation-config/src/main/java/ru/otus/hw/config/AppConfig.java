package ru.otus.hw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.StreamsIOService;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
@ComponentScan(basePackages = "ru.otus")
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public IOService ioService(
            @Value("#{T(System).out}") PrintStream out,
            @Value("#{T(System).in}") InputStream in) {
        return new StreamsIOService(out, in);
    }
}
