package az.company.elibrary.service.genre;

import az.company.elibrary.models.entity.Genre;
import az.company.elibrary.models.request.GenreRequest;
import az.company.elibrary.models.response.GenreResponse;
import az.company.elibrary.repository.GenreRepository;
import az.company.elibrary.service.getter.CommonGetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.company.elibrary.mapper.GenreMapper.GENRE_MAPPER;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService{

    private final CommonGetterService getterService;
    private final GenreRepository genreRepository;

    @Override
    public GenreResponse get(Long id) {
        Genre genre = getterService.getGenre(id);
        return GENRE_MAPPER.mapToGenreResponse(genre);
    }

    @Override
    public List<GenreResponse> getAll() {
        return genreRepository.findAll().stream()
                .map(GENRE_MAPPER::mapToGenreResponse)
                .toList();
    }

    @Override
    public GenreResponse add(GenreRequest genreRequest) {
        Genre genre = GENRE_MAPPER.mapToGenreEntity(genreRequest);
        genreRepository.save(genre);
        return GENRE_MAPPER.mapToGenreResponse(genre);
    }

    @Override
    public GenreResponse update(Long id, GenreRequest genreRequest) {
        Genre genre = getterService.getGenre(id);
        genre.setName(genreRequest.getName());
        genreRepository.save(genre);
        return GENRE_MAPPER.mapToGenreResponse(genre);
    }

    @Override
    public void delete(Long id) {
        genreRepository.deleteById(id);
    }

}
