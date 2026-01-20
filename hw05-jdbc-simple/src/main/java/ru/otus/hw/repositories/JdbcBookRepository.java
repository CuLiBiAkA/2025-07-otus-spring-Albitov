package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private static final BookRowMapper BOOK_ROW_MAPPER = new BookRowMapper();

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Optional<Book> findById(long id) {
        try {
            return jdbcOperations.query(
                    """
                            SELECT
                                b.id            AS book_id,
                                b.title         AS book_title,
                                a.id            AS author_id,
                                a.full_name     AS author_name,
                                g.id            AS genre_id,
                                g.name          AS genre_name
                            FROM books b
                            LEFT JOIN authors a ON a.id = b.author_id
                            LEFT JOIN genres  g ON g.id = b.genre_id
                            """
                            + " WHERE b.id = :id",
                    Map.of("id", id),
                    BOOK_ROW_MAPPER
            ).stream().findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        return jdbcOperations.query(
                """
                        SELECT
                            b.id            AS book_id,
                            b.title         AS book_title,
                            a.id            AS author_id,
                            a.full_name     AS author_name,
                            g.id            AS genre_id,
                            g.name          AS genre_name
                        FROM books b
                        LEFT JOIN authors a ON a.id = b.author_id
                        LEFT JOIN genres  g ON g.id = b.genre_id
                        """,
                BOOK_ROW_MAPPER);
    }

    @Override
    public Book save(Book book) {
        return book.getId() == 0 ? insert(book) : update(book);
    }

    @Override
    public void deleteById(long id) {
        jdbcOperations.update(
                "DELETE FROM books WHERE id = :id",
                Map.of("id", id)
        );
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        var params = new MapSqlParameterSource();
        params.addValues(Map.of(
                "title", book.getTitle(),
                "authorId", book.getAuthor().getId(),
                "genreId", book.getGenre().getId()
        ));

        jdbcOperations.update(
                """
                        INSERT INTO books (title, author_id, genre_id)
                        VALUES (:title, :authorId, :genreId)
                        """,
                params,
                keyHolder,
                new String[]{"id"}
        );

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        int updated = jdbcOperations.update(
                """
                        UPDATE books
                           SET title = :title,
                               author_id = :authorId,
                               genre_id = :genreId
                         WHERE id = :id
                        """,
                Map.of(
                        "id", book.getId(),
                        "title", book.getTitle(),
                        "authorId", book.getAuthor().getId(),
                        "genreId", book.getGenre().getId()
                )
        );

        if (updated == 0) {
            throw new EntityNotFoundException(
                    "Book with id %d not found".formatted(book.getId())
            );
        }

        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(
                    rs.getLong("book_id"),
                    rs.getString("book_title"),
                    new Author(
                            rs.getLong("author_id"),
                            rs.getString("author_name")
                    ),
                    new Genre(
                            rs.getLong("genre_id"),
                            rs.getString("genre_name")
                    )
            );
        }
    }
}
