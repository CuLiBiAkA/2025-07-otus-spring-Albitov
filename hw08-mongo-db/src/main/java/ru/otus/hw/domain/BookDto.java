package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class BookDto {
    private final String id;

    private final String title;

    private final AuthorDto author;

    private final GenreDto genre;
}