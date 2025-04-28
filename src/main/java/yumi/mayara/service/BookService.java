package yumi.mayara.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import yumi.mayara.dto.filter.BookFilter;
import yumi.mayara.dto.request.AddBookRequest;
import yumi.mayara.dto.request.UpdateBookRequest;
import yumi.mayara.dto.response.BookResponse;
import yumi.mayara.entity.Book;
import yumi.mayara.exception.BookNotFoundException;
import yumi.mayara.exception.BusinessException;
import yumi.mayara.repository.BookRepository;
import yumi.mayara.repository.specification.BookSpecification;

import java.util.List;

@Service
public class BookService {

    private BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<BookResponse> findAllBooks(BookFilter filter) {
        Specification<Book> query = Specification.allOf(BookSpecification.hasAuthor(filter.getAuthor()),
                                                        BookSpecification.hasISBN(filter.getISBN()),
                                                        BookSpecification.hasTitle(filter.getTitle()));

        return repository.findAll(query).stream().map(Book::toResponse).toList();
    }

    public BookResponse addBook(AddBookRequest bookToAdd) {
        if(bookToAdd.pagesRead() > bookToAdd.totalPages()) {
            throw new BusinessException("The pages read cannot be more than the book's total pages.");
        }
        Book addedBook = repository.save(bookToAdd.toBook());
        return addedBook.toResponse();
    }

    public BookResponse updateBook(Long id, UpdateBookRequest updatedBook) {
        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException("The book with ID " + id + " not found."));

        if(updatedBook.pagesRead() > updatedBook.totalPages()) {
            throw new BusinessException("The pages read cannot be more than the book's total pages.");
        }

        book.setAuthor(updatedBook.author());
        book.setISBN(updatedBook.ISBN());
        book.setTitle(updatedBook.title());
        book.setTotalPages(updatedBook.totalPages());
        book.setPagesRead(updatedBook.pagesRead());

        book = repository.save(book);

        return book.toResponse();
    }

    public void deleteBook(Long id) {
        Book book = repository.findById(id).orElseThrow(() -> new BookNotFoundException("The book with ID " + id + " not found."));
        repository.delete(book);
    }

}
