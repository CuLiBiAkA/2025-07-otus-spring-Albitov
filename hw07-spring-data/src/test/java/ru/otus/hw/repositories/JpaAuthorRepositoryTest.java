package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class JpaAuthorRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private AuthorRepository repository;

    @Test
    void shouldFindAllAuthors() {
        var authors = repository.findAll();

        assertThat(authors)
                .containsExactly(
                        new Author(1L, "Author_1" ),
                        new Author(2L, "Author_2" ),
                        new Author(3L, "Author_3" )
                );
    }

    @Test
    void shouldFindAuthorById() {
        var author = repository.findById(1L);

        assertThat(author)
                .isPresent()
                .get()
                .isEqualTo(new Author(1L, "Author_1" ));
    }
}
