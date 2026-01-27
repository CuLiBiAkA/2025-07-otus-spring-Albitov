package ru.otus.hw.services;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.JpaBookRepository;
import ru.otus.hw.repositories.JpaCommentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;


@DataJpaTest
@Import({
        CommentServiceImpl.class,
        JpaCommentRepository.class,
        JpaBookRepository.class
})
class CommentServiceImplTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private CommentService commentService;

    @Test
    void shouldLoadCommentWithBookWithoutLazyException() {
        var book = em.find(Book.class, 1L);
        var comment = new Comment(0, "Test comment", book);
        em.persist(comment);
        em.flush();
        em.clear();

        var loaded = commentService.findAllByBookId(book.getId());

        assertThat(loaded).isNotEmpty();

        assertThatCode(() -> loaded.get(0).getText())
                .doesNotThrowAnyException();
    }
}
