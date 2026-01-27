package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaGenreRepository implements GenreRepository {

    private final EntityManager em;

    @Override
    public List<Genre> findAll() {
        return em.createQuery(
                "select g from Genre g",
                Genre.class
        ).getResultList();
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }
}
