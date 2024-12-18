package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private BookDAO bookDAOMock;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookDAOMock = Mockito.mock(BookDAO.class);
        bookService = new BookService(bookDAOMock);
    }

    @Test
    void testAddBook() {
        Book book = new Book("Effective Java", "Joshua Bloch", "1234567890", 2008);

        bookService.addBook(book);

        // Vérifie que la méthode add du DAO a été appelée
        verify(bookDAOMock, times(1)).add(book);
    }

    @Test
    void testUpdateBook() {
        Book existingBook = new Book("Clean Code", "Robert C. Martin", "987654321", 2008);
        existingBook.setId(1);

        // Mock du comportement du DAO
        when(bookDAOMock.getBookById(1)).thenReturn(Optional.of(existingBook));

        bookService.updateBook(1, "Clean Code Updated", "Uncle Bob");

        // Vérifie que les propriétés du livre ont été mises à jour
        assertEquals("Clean Code Updated", existingBook.getTitle());
        assertEquals("Uncle Bob", existingBook.getAuthor());

        // Vérifie que la méthode update du DAO a été appelée
        verify(bookDAOMock, times(1)).update(existingBook);
    }

    @Test
    void testUpdateBookNotFound() {
        when(bookDAOMock.getBookById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bookService.updateBook(1, "New Title", "New Author");
        });

        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void testFindBookById() {
        Book book1 = new Book("Book 1", "Author 1", "111", 2020);
        book1.setId(1);
        Book book2 = new Book("Book 2", "Author 2", "222", 2021);
        book2.setId(2);

        when(bookDAOMock.getAllBooks()).thenReturn(Arrays.asList(book1, book2));

        Book result = bookService.findBookById(1);

        assertNotNull(result);
        assertEquals("Book 1", result.getTitle());
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(
                new Book("Book 1", "Author 1", "111", 2020),
                new Book("Book 2", "Author 2", "222", 2021)
        );

        when(bookDAOMock.getAllBooks()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("Book 1", result.get(0).getTitle());
    }

    @Test
    void testDisplayBooks() {
        List<Book> books = Arrays.asList(
                new Book("Book 1", "Author 1", "111", 2020),
                new Book("Book 2", "Author 2", "222", 2021)
        );

        when(bookDAOMock.getAllBooks()).thenReturn(books);

        bookService.displayBooks();

        // Vérifie que la méthode getAllBooks du DAO a été appelée
        verify(bookDAOMock, times(1)).getAllBooks();
    }
}