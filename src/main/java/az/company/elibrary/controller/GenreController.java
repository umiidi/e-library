package az.company.elibrary.controller;

import az.company.elibrary.models.request.GenreRequest;
import az.company.elibrary.models.response.GenreResponse;
import az.company.elibrary.service.genre.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.get(id));
    }

    @GetMapping
    public ResponseEntity<List<GenreResponse>> getAll() {
        return ResponseEntity.ok(genreService.getAll());
    }

    @PostMapping
    public ResponseEntity<GenreResponse> add(@Valid @RequestBody GenreRequest genreRequest) {
        return ResponseEntity.ok(genreService.add(genreRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreResponse> update(@PathVariable Long id,
                                                @Valid @RequestBody GenreRequest genreRequest) {
        return ResponseEntity.ok(genreService.update(id, genreRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
