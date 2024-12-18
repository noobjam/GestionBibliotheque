package com.library;
import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.StudentDAO;
import com.library.model.Book;
import com.library.model.Student;
import com.library.model.Borrow;
import com.library.service.BookService;
import com.library.service.BorrowService;
import com.library.service.StudentService;

import java.util.Date;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Créer les DAO
        BookDAO bookDAO = new BookDAO();
        StudentDAO studentDAO = new StudentDAO();
        BorrowDAO borrowDAO = new BorrowDAO();

        // Créer les services
        BookService bookService = new BookService(bookDAO);
        StudentService studentService = new StudentService(studentDAO);
        BorrowService borrowService = new BorrowService(borrowDAO);

        boolean running = true;

        while (running) {
            try {
                System.out.println("\n===== Menu =====");
                System.out.println("1. Ajouter un livre");
                System.out.println("2. Afficher les livres");
                System.out.println("3. Ajouter un étudiant");
                System.out.println("4. Afficher les étudiants");
                System.out.println("5. Emprunter un livre");
                System.out.println("6. Afficher les emprunts");
                System.out.println("7. Quitter");

                System.out.print("Choisir une option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        System.out.print("Entrez le titre du livre: ");
                        String title = scanner.nextLine();
                        System.out.print("Entrez l'auteur du livre: ");
                        String author = scanner.nextLine();
                        System.out.print("Entrez le code du livre: ");
                        String code = scanner.nextLine();
                        System.out.print("Entrez l'année de publication du livre: ");
                        int year = scanner.nextInt();
                        scanner.nextLine();

                        Book book = new Book(title, author, code, year);
                        bookService.addBook(book);
                        System.out.println("Livre ajouté avec succès !");
                        break;

                    case 2:
                        bookService.displayBooks();
                        break;

                    case 3:
                        System.out.print("Entrez l'Id de l'étudiant: ");
                        int idstudent = scanner.nextInt();  // Corrected to int
                        scanner.nextLine();  // Consume the leftover newline character
                        System.out.print("Entrez le nom de l'étudiant: ");
                        String studentName = scanner.nextLine();

                        Student student = new Student(idstudent, studentName);  // Corrected to match constructor with int id
                        studentService.addStudent(student);
                        System.out.println("Étudiant ajouté avec succès !");
                        break;


                    case 4:
                        studentService.displayStudents();
                        break;

                    case 5:
                        System.out.print("Entrez l'ID de l'étudiant: ");
                        int studenId = getIntInput(scanner);
                        System.out.print("Entrez l'ID du livre: ");
                        int bookId = getIntInput(scanner);

                        Student studentForBorrow = studentService.findStudentById(studenId);
                        Book bookForBorrow = bookService.findBookById(bookId);

                        if (studentForBorrow != null && bookForBorrow != null) {
                            Borrow borrow = new Borrow(studentForBorrow.getId(), studentForBorrow, bookForBorrow, new Date(), null);
                            borrowService.borrowBook(borrow);
                            System.out.println("Livre emprunté avec succès !");
                        } else {
                            System.out.println("Étudiant ou livre introuvable.");
                        }
                        break;

                    case 6:
                        borrowService.displayBorrows();
                        break;

                    case 7:
                        running = false;
                        System.out.println("Au revoir!");
                        break;

                    default:
                        System.out.println("Option invalide.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide, veuillez saisir un nombre.");
                scanner.nextLine(); // Clear invalid input from scanner buffer
            }
        }

        scanner.close();
    }

    private static int getIntInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Veuillez saisir un nombre valide: ");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
}