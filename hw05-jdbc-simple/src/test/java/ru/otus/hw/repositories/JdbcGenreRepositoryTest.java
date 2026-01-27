package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JdbcGenreRepository" )
@JdbcTest
@Import(JdbcGenreRepository.class)
class JdbcGenreRepositoryTest {

    @Autowired
    private JdbcGenreRepository repository;

    @DisplayName("должен возвращать все жанры" )
    @Test
    void shouldFindAllGenres() {
        var actualGenres = repository.findAll();

        var expectedGenres = List.of(
                new Genre(1L, "Genre_1" ),
                new Genre(2L, "Genre_2" ),
                new Genre(3L, "Genre_3" ),
                new Genre(4L, "Genre_4"),
                new Genre(5L, "Genre_5")
        );

        assertThat(actualGenres)
                .containsExactlyElementsOf(expectedGenres);
    }

    @DisplayName("должен возвращать жанр по id" )
    @Test
    void shouldFindGenreById() {
        var actualGenre = repository.findById(2L);

        assertThat(actualGenre)
                .isPresent()
                .get()
                .isEqualTo(new Genre(2L, "Genre_2" ));
    }

    @DisplayName("должен возвращать empty, если жанр не найден" )
    @Test
    void shouldReturnEmptyWhenGenreNotFound() {
        var actualGenre = repository.findById(999L);

        assertThat(actualGenre).isEmpty();
    }
}
