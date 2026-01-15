package ru.otus.hw.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JpaCommentRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private CommentRepository repository;

    @Test
    void shouldSaveAndFindCommentById() {
        var book = em.find(Book.class, 1L);

        var comment = new Comment(0, "Nice book", book);
        repository.save(comment);

        em.flush();
        em.clear();

        var actual = repository.findById(comment.getId());

        assertThat(actual).isPresent();
        assertThat(actual.get().getText()).isEqualTo("Nice book" );
        assertThat(actual.get().getBook().getId()).isEqualTo(1L);
    }

    @Test
    void shouldFindCommentsByBookId() {
        var book = em.find(Book.class, 1L);

        repository.save(new Comment(0, "A", book));
        repository.save(new Comment(0, "B", book));

        em.flush();
        em.clear();

        var comments = repository.findAllByBookId(1L);

        assertThat(comments)
                .hasSizeGreaterThanOrEqualTo(2)
                .allMatch(c -> c.getBook().getId() == 1L);
    }
}
