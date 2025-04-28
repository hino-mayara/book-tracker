package yumi.mayara.service;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import yumi.mayara.dto.request.AddBookRequest;
import yumi.mayara.dto.request.UpdateBookRequest;
import yumi.mayara.dto.response.BookResponse;
import yumi.mayara.entity.Book;
import yumi.mayara.exception.BookNotFoundException;
import yumi.mayara.exception.BusinessException;
import yumi.mayara.repository.BookRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookServiceTest {


    @Mock
    private BookRepository repository;

    @InjectMocks
    private BookService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldAddBookSuccessfully() {
        AddBookRequest request = new AddBookRequest(
                "Effective Java",
                "123456789",
                "Joshua Bloch",
                400,
                100
        );

        Book book = request.toBook();
        book.setId(1L);

        when(repository.save(any(Book.class))).thenReturn(book);

        BookResponse response = service.addBook(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Effective Java", response.title());
        assertEquals("123456789", response.ISBN());
        assertEquals("Joshua Bloch", response.author());
        assertEquals(400, response.totalPages());
        assertEquals(100, response.pagesRead());

        verify(repository, times(1)).save(any(Book.class));
    }

    @Test
    public void shouldThrowBusinessExceptionWhenPagesReadGreaterThanTotalPages() {
        AddBookRequest request = new AddBookRequest(
                "Clean Code",
                "987654321",
                "Robert Martin",
                300,
                400
        );

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.addBook(request);
        });

        assertEquals("The pages read cannot be more than the book's total pages.", exception.getMessage());
        verify(repository, never()).save(any(Book.class));
    }

    @Test
    void shouldUpdateBookSuccessfully() {
        Long bookId = 1L;

        Book existingBook = Book.builder()
                .id(bookId)
                .title("Old Title")
                .author("Old Author")
                .ISBN("111111")
                .totalPages(100)
                .pagesRead(20)
                .build();

        UpdateBookRequest request = new UpdateBookRequest(
                "New Title",
                "222222",
                "New Author",
                200,
                50
        );

        Book updatedBook = Book.builder()
                .id(bookId)
                .title(request.title())
                .author(request.author())
                .ISBN(request.ISBN())
                .totalPages(request.totalPages())
                .pagesRead(request.pagesRead())
                .build();

        when(repository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(repository.save(any(Book.class))).thenReturn(updatedBook);

        BookResponse response = service.updateBook(bookId, request);

        assertNotNull(response);
        assertEquals(bookId, response.id());
        assertEquals("New Title", response.title());
        assertEquals("222222", response.ISBN());
        assertEquals("New Author", response.author());
        assertEquals(200, response.totalPages());
        assertEquals(50, response.pagesRead());

        verify(repository).findById(bookId);
        verify(repository).save(any(Book.class));
    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenBookDoesNotExist() {
        Long bookId = 2L;
        UpdateBookRequest request = new UpdateBookRequest(
                "Any Title",
                "333333",
                "Any Author",
                100,
                10
        );

        when(repository.findById(bookId)).thenReturn(Optional.empty());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            service.updateBook(bookId, request);
        });

        assertEquals("The book with ID " + bookId + " not found.", exception.getMessage());

        verify(repository).findById(bookId);
        verify(repository, never()).save(any(Book.class));
    }

    @Test
    void shouldThrowBusinessExceptionWhenUpdatingPagesReadGreaterThanTotalPages() {
        Long bookId = 3L;

        Book existingBook = Book.builder()
                .id(bookId)
                .title("Another Title")
                .author("Another Author")
                .ISBN("444444")
                .totalPages(300)
                .pagesRead(100)
                .build();

        UpdateBookRequest invalidRequest = new UpdateBookRequest(
                "Another Updated Title",
                "555555",
                "Another Updated Author",
                150,
                200
        );

        when(repository.findById(bookId)).thenReturn(Optional.of(existingBook));

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            service.updateBook(bookId, invalidRequest);
        });

        assertEquals("The pages read cannot be more than the book's total pages.", exception.getMessage());

        verify(repository).findById(bookId);
        verify(repository, never()).save(any(Book.class));
    }
}
