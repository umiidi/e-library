package az.company.elibrary.mapper;

import az.company.elibrary.models.entity.Author;
import az.company.elibrary.models.request.AuthorRequest;
import az.company.elibrary.models.response.AuthorResponse;

public enum AuthorMapper {

    AUTHOR_MAPPER;

    public Author mapToAuthorEntity(AuthorRequest authorRequest) {
        return Author.builder()
                .firstName(authorRequest.getFirstName())
                .lastName(authorRequest.getLastName())
                .dateOfBirth(authorRequest.getDateOfBirth())
                .dateOfDeath(authorRequest.getDateOfDeath())
                .biography(authorRequest.getBiography())
                .build();
    }

    public AuthorResponse mapToAuthorResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .dateOfBirth(author.getDateOfBirth())
                .dateOfDeath(author.getDateOfDeath())
                .biography(author.getBiography())
                .build();
    }

    public void updateEntity(AuthorRequest authorRequest, Author author) {
        author.setFirstName(authorRequest.getFirstName());
        author.setLastName(authorRequest.getLastName());
        author.setDateOfBirth(authorRequest.getDateOfBirth());
        author.setDateOfDeath(authorRequest.getDateOfDeath());
        author.setBiography(authorRequest.getBiography());
    }

}
