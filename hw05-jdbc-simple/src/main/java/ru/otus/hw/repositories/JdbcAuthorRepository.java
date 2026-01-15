package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcAuthorRepository implements AuthorRepository {

    private static final AuthorRowMapper AUTHOR_ROW_MAPPER = new AuthorRowMapper();

    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<Author> findAll() {
        try {
            return jdbcOperations.query(
                    "SELECT id, full_name FROM authors",
                    AUTHOR_ROW_MAPPER
            );
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Author> findById(long id) {
        try {
            return jdbcOperations.query(
                    "SELECT id, full_name FROM authors WHERE id = :id",
                    Map.of("id", id),
                    AUTHOR_ROW_MAPPER
            ).stream().findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            return new Author(
                    rs.getLong("id"),
                    rs.getString("full_name")
            );
        }
    }
}
