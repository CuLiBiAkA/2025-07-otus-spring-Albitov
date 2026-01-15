package ru.otus.hw.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DataMongoTest
@Import({
        CommentServiceImpl.class
})
class CommentServiceImplTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private MongoOperations mongoOperations;

    private String bookId;
    private String commentId;

    @Test
    void shouldLoadCommentWithBookWithoutException() {
        var book = mongoOperations.insert(
                new Book(null, "Book for comments",
                        new Author("author1", ""),
                        new Genre("genre1", "")
                )
        );

        bookId = book.getId();

        var comment = mongoOperations.insert(
                new Comment(bookId, "Test comment", book)
        );
        commentId = comment.getId();

        var loaded = commentService.findAllByBookId(bookId);

        assertThat(loaded).hasSize(1);

        assertThatCode(() -> loaded.get(0).getText())
                .doesNotThrowAnyException();

        assertThat(loaded.get(0).getText()).isEqualTo("Test comment" );
    }

    @AfterEach
    void cleanUp() {
        if (commentId != null) {
            mongoOperations.remove(
                    new Query(Criteria.where("_id" ).is(commentId)),
                    Comment.class
            );
        }
        if (bookId != null) {
            mongoOperations.remove(
                    new Query(Criteria.where("_id" ).is(bookId)),
                    Book.class
            );
        }
    }
}
