package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaBookRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookRepository repository;

    @Test
    void shouldFindBookByIdWithRelations() {
        var book = repository.findById(1L);

        assertThat(book).isPresent();

        var b = book.get();
        assertThat(b.getTitle()).isEqualTo("BookTitle_1" );
        assertThat(b.getAuthor().getFullName()).isEqualTo("Author_1" );
        assertThat(b.getGenre().getName()).isEqualTo("Genre_1" );
    }

    @Test
    void shouldFindAllBooksOrdered() {
        var books = repository.findAll();

        assertThat(books)
                .hasSize(3)
                .isSortedAccordingTo(
                        Comparator.comparingLong(Book::getId)
                );
    }

    @Test
    void shouldSaveAndDeleteBook() {
        var author = em.find(Author.class, 1L);
        var genre = em.find(Genre.class, 1L);

        var book = new Book(0, "New Book", author, genre);
        repository.save(book);

        em.flush();
        em.clear();

        assertThat(repository.findById(book.getId())).isPresent();

        repository.deleteById(book.getId());

        assertThat(repository.findById(book.getId())).isEmpty();
    }
}
