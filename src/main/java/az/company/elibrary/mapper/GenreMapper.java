package az.company.elibrary.mapper;

import az.company.elibrary.models.entity.Genre;
import az.company.elibrary.models.request.GenreRequest;
import az.company.elibrary.models.response.GenreResponse;

public enum GenreMapper {

    GENRE_MAPPER;

    public Genre mapToGenreEntity(GenreRequest genreRequest) {
        return Genre.builder()
                .name(genreRequest.getName())
                .build();
    }

    public GenreResponse mapToGenreResponse(Genre genre) {
        return GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
