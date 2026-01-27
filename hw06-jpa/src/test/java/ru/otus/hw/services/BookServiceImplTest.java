package ru.otus.hw.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.GenreConverter;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.JpaAuthorRepository;
import ru.otus.hw.repositories.JpaBookRepository;
import ru.otus.hw.repositories.JpaGenreRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DataJpaTest
@Import({
        BookServiceImpl.class,
        JpaBookRepository.class,
        JpaAuthorRepository.class,
        JpaGenreRepository.class,
        BookConverter.class,
        AuthorConverter.class,
        GenreConverter.class
})
class BookServiceImplTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookService bookService;

    @Test
    void shouldLoadBookWithAuthorAndGenre() {
        var author = em.find(Author.class, 1L);
        var genre  = em.find(Genre.class, 1L);

        var saved = bookService.update(
                0L,
                "Test book",
                author.getId(),
                genre.getId()
        );

        em.clear(); // критично

        // WHEN
        var loaded = bookService.findById(saved.getId()).orElseThrow();

        // THEN
        assertThatCode(() -> loaded.getAuthor().getFullName())
                .doesNotThrowAnyException();

        assertThatCode(() -> loaded.getGenre().getName())
                .doesNotThrowAnyException();
    }
}


