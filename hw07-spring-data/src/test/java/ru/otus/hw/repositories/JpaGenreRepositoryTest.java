package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaGenreRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private GenreRepository repository;

    @Test
    void shouldFindAllGenres() {
        var genres = repository.findAll();

        assertThat(genres)
                .containsExactly(
                        new Genre(1L, "Genre_1" ),
                        new Genre(2L, "Genre_2" ),
                        new Genre(3L, "Genre_3" ),
                        new Genre(4L, "Genre_4" ),
                        new Genre(5L, "Genre_5" )
                );
    }

    @Test
    void shouldFindGenreById() {
        var genre = repository.findById(2L);

        assertThat(genre)
                .isPresent()
                .get()
                .isEqualTo(new Genre(2L, "Genre_2" ));
    }
}
