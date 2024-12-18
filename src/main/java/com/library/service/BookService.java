package com.library.service;

import com.library.dao.BookDAO;
import com.library.model.Book;

import java.util.List;

public class BookService {
    private final BookDAO bookDAO;

    // Constructeur pour injecter le DAO
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public void addBook(Book book) {
        bookDAO.add(book);
    }
    public void updateBook(int bookId, String title, String author) {
        Book book = bookDAO.getBookById(bookId).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        book.setTitle(title);
        book.setAuthor(author);
        bookDAO.update(book);  // Update book in the DAO
    }


    public Book findBookById(int bookId) {
        for (Book book : getAllBooks()) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        return null;  // Si le livre n'est pas trouvé
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public void displayBooks() {
        List<Book> books = bookDAO.getAllBooks();
        for (Book book : books) {
            System.out.println(book.getId() + ": " + book.getTitle() + " by " + book.getAuthor());
        }
    }



}