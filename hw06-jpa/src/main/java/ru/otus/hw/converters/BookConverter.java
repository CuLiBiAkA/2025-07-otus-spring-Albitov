package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.domain.AuthorDto;
import ru.otus.hw.domain.BookDto;
import ru.otus.hw.domain.GenreDto;
import ru.otus.hw.models.Book;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    public String bookToString(BookDto book) {
        return "Id: %d, title: %s, author: {%s}, genres: [%s]".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                genreConverter.genreToString(book.getGenre()));
    }

    public BookDto convertEntityToDto(Book book) {
        var author = new AuthorDto(book.getAuthor().getId(), book.getAuthor().getFullName());
        var gener = new GenreDto(book.getGenre().getId(), book.getGenre().getName());
        return new BookDto(book.getId(), book.getTitle(), author, gener);
    }
}

