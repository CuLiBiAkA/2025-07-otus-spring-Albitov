package ru.otus.hw.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.GenreConverter;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest

@Import({
        BookServiceImpl.class,
        BookConverter.class,
        AuthorConverter.class,
        GenreConverter.class
})
class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private MongoOperations mongoOperations;

    private String bookId;

    @Test
    void shouldSaveAndLoadBook() {
        var author = mongoOperations.insert(new Author("a1", "Author_1" ));
        var genre1 = mongoOperations.insert(new Genre("g1", "Genre_1" ));

        var saved = bookService.insert(
                "Test Book",
                author.getId(),
                genre1.getId()
        );
        bookId = saved.getId();

        var loaded = bookService.findById(bookId);

        // then
        assertThat(loaded).isPresent();

        assertThat(loaded.get().getTitle()).isEqualTo("Test Book" );
        assertThat(loaded.get().getAuthor().getFullName()).isEqualTo("Author_1");
        assertThat(loaded.get().getGenre().getName()).isEqualTo("Genre_1");
    }

    @AfterEach
    void cleanUp() {
        if (bookId != null) {
            mongoOperations.remove(
                    new Query(Criteria.where("_id" ).is(bookId)),
                    Book.class
            );
        }
        mongoOperations.remove(new Query(), Author.class);
        mongoOperations.remove(new Query(), Genre.class);
    }
}
