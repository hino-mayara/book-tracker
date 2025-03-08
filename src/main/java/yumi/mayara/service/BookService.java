package yumi.mayara.service;

import org.springframework.stereotype.Service;
import yumi.mayara.entity.Book;
import yumi.mayara.repository.BookRepository;

import java.util.List;

@Service
public class BookService {

    private BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findAllBooks() {
        return repository.findAll();
    }
}
