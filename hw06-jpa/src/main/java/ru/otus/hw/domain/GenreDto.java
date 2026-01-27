package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class GenreDto {
    private final long id;

    private final String name;
}