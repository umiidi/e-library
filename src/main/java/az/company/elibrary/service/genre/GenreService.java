package az.company.elibrary.service.genre;

import az.company.elibrary.models.request.GenreRequest;
import az.company.elibrary.models.response.GenreResponse;

import java.util.List;

public interface GenreService {

    GenreResponse get(Long id);

    List<GenreResponse> getAll();

    GenreResponse add(GenreRequest genreRequest);

    GenreResponse update(Long id, GenreRequest genreRequest);

    void delete(Long id);

}
