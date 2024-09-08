package az.company.elibrary.mapper;

import az.company.elibrary.models.entity.Book;
import az.company.elibrary.models.request.BookRequest;
import az.company.elibrary.models.response.BookResponse;

import static az.company.elibrary.mapper.CategoryMapper.CATEGORY_MAPPER;
import static az.company.elibrary.mapper.GenreMapper.GENRE_MAPPER;

public enum BookMapper {

    BOOK_MAPPER;

    public Book mapToBookEntity(BookRequest bookRequest) {
        return Book.builder()
                .title(bookRequest.getTitle())
                .description(bookRequest.getDescription())
                .isbn(bookRequest.getIsbn())
                .publisher(bookRequest.getPublisher())
                .publicationYear(bookRequest.getPublicationYear())
                .inventory(bookRequest.getInventory())
                .language(bookRequest.getLanguage())
                .build();
    }

    public BookResponse mapToBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .description(book.getDescription())
                .authorId(book.getAuthor().getId())
                .isbn(book.getIsbn())
                .publisher(book.getPublisher())
                .publicationYear(book.getPublicationYear())
                .inventory(book.getInventory())
                .language(book.getLanguage())
                .category(CATEGORY_MAPPER.mapToCategoryResponse(book.getCategory()))
                .genres(book.getGenres().stream()
                        .map(GENRE_MAPPER::mapToGenreResponse)
                        .toList())
                .build();
    }

}
