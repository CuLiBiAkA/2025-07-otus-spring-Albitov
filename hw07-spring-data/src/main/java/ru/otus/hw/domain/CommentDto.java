package ru.otus.hw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class CommentDto {
    private final long id;

    private final String text;
}