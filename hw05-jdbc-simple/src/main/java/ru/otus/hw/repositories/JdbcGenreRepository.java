package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcGenreRepository implements GenreRepository {

    private static final GnreRowMapper GNRE_ROW_MAPPER = new GnreRowMapper();


    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public List<Genre> findAll() {
        try {
            return jdbcOperations.query(
                    "SELECT id, name FROM genres ORDER BY id",
                    GNRE_ROW_MAPPER
            );
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Genre> findById(long id) {
        try {
            return jdbcOperations.query(
                    "SELECT id, name FROM genres WHERE id = :id",
                    Map.of("id", id),
                    GNRE_ROW_MAPPER
            ).stream().findFirst();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static class GnreRowMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            return new Genre(
                    rs.getLong("id"),
                    rs.getString("name")
            );
        }
    }
}
