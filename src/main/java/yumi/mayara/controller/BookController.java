package yumi.mayara.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yumi.mayara.dto.filter.BookFilter;
import yumi.mayara.dto.request.AddBookRequest;
import yumi.mayara.dto.request.UpdateBookRequest;
import yumi.mayara.dto.response.BookResponse;
import yumi.mayara.service.BookService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/v1/books")
public class BookController {

    private BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookResponse>> findAllBooks(BookFilter filter) {
        List<BookResponse> result = service.findAllBooks(filter);

        if(Objects.isNull(result) || result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody AddBookRequest request) {
        BookResponse addedBook = service.addBook(request);

        return ResponseEntity.ok(addedBook);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> updateBook(@PathVariable("id") Long id, @Valid @RequestBody UpdateBookRequest request) {
        BookResponse updatedBook = service.updateBook(id, request);

        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        service.deleteBook(id);

        return ResponseEntity.ok().build();
    }

}
