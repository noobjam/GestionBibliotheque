package com.library;


import com.library.dao.BorrowDAO;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.model.Book;
import com.library.service.BorrowService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BorrowServiceTest {

    private BorrowDAO borrowDAO;
    private BorrowService borrowService;

    @BeforeEach
    void setUp() {
        // Initialisation de BorrowDAO simulé
        borrowDAO = mock(BorrowDAO.class);

        // Initialisation du service avec le DAO simulé
        borrowService = new BorrowService(borrowDAO);
    }

    @Test
    void testBorrowBook() {
        // Création d'un emprunt
        Student student = new Student(1, "John Doe");
        Book book = new Book("Effective Java", "Joshua Bloch", "1234567890", 2008);
        Borrow borrow = new Borrow(1, student, book, new Date(), null);

        // Simuler la méthode save de BorrowDAO
        doNothing().when(borrowDAO).save(borrow);

        // Appel de la méthode borrowBook
        borrowService.borrowBook(borrow);

        // Vérifier que la méthode save a bien été appelée
        verify(borrowDAO, times(1)).save(borrow);
    }

    @Test
    void testGetAllBorrows() {
        // Création de la liste des emprunts
        Student student = new Student(1, "John Doe");
        Book book = new Book("Effective Java", "Joshua Bloch", "1234567890", 2008);
        Borrow borrow1 = new Borrow(1, student, book, new Date(), null);
        Borrow borrow2 = new Borrow(2, student, book, new Date(), new Date());

        List<Borrow> borrows = new ArrayList<>();
        borrows.add(borrow1);
        borrows.add(borrow2);

        // Simuler la méthode getAllBorrows de BorrowDAO
        when(borrowDAO.getAllBorrows()).thenReturn(borrows);

        // Appel de la méthode getAllBorrows
        List<Borrow> result = borrowService.getAllBorrows();

        // Vérifier que la méthode retourne bien les emprunts
        assertEquals(2, result.size());
        assertEquals(borrow1, result.get(0));
        assertEquals(borrow2, result.get(1));
    }

    @Test
    void testReturnBook() {
        // ID d'un emprunt à supprimer
        int borrowId = 1;

        // Simuler la méthode delete de BorrowDAO
        doNothing().when(borrowDAO).delete(borrowId);

        // Appel de la méthode returnBook
        borrowService.returnBook(borrowId);

        // Vérifier que la méthode delete a bien été appelée
        verify(borrowDAO, times(1)).delete(borrowId);
    }

    @Test
    void testDisplayBorrows() {
        // Arrange
        BorrowDAO mockBorrowDAO = mock(BorrowDAO.class);
        List<Borrow> mockBorrows = new ArrayList<>();
        mockBorrows.add(new Borrow(1, new Student(1, "John Doe"), new Book("Effective Java", "Joshua Bloch", "1234567890", 2008), new Date(), null));
        when(mockBorrowDAO.getAllBorrows()).thenReturn(mockBorrows);

        BorrowService borrowService = new BorrowService(mockBorrowDAO);

        // Act
        borrowService.displayBorrows();

        // Assert
        // Use assertions based on expected printed output or any other method to verify the result
    }

}