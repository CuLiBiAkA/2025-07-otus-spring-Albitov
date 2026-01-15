package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class BookDto {
    private final long id;

    private final String title;

    private final AuthorDto author;

    private final GenreDto genre;
}