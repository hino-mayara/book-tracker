package yumi.mayara.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yumi.mayara.entity.Book;
import yumi.mayara.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/v1/books")
public class BookController {

    private BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> findAllBooks() {
        List<Book> result = service.findAllBooks();
        return ResponseEntity.ok(result);
    }
}
