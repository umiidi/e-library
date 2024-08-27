package az.company.elibrary.mapper;

import az.company.elibrary.models.entity.Author;
import az.company.elibrary.models.entity.Book;
import az.company.elibrary.models.request.BookRequest;
import az.company.elibrary.models.response.BookResponse;

public enum BookMapper {

    BOOK_MAPPER;

    public Book mapToBookEntity(BookRequest bookRequest, Author author) {
        return Book.builder()
                .title(bookRequest.getTitle())
                .author(author)
                .isbn(bookRequest.getIsbn())
                .publisher(bookRequest.getPublisher())
                .publicationYear(bookRequest.getPublicationYear())
                .inventory(bookRequest.getInventory())
                .build();
    }

    public BookResponse mapToBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorId(book.getAuthor().getId())
                .isbn(book.getIsbn())
                .publisher(book.getPublisher())
                .publicationYear(book.getPublicationYear())
                .inventory(book.getInventory())
                .build();
    }

    public void updateEntity(BookRequest bookRequest, Book book) {
        book.setTitle(bookRequest.getTitle());
//        book.setAuthor(new Author(bookRequest.getAuthorId()));
        book.setIsbn(bookRequest.getIsbn());
        book.setPublisher(bookRequest.getPublisher());
        book.setPublicationYear(bookRequest.getPublicationYear());
        book.setInventory(bookRequest.getInventory());
    }

}
