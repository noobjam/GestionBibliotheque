package com.library.service;

import com.library.dao.BorrowDAO;
import com.library.model.Borrow;

import java.util.List;

public class BorrowService {
    private BorrowDAO borrowDAO;

    public BorrowService(BorrowDAO borrowDAO) {
        this.borrowDAO = borrowDAO;
    }


    public void borrowBook(Borrow borrow) {
        borrowDAO.save(borrow);
    }

    public List<Borrow> getAllBorrows() {
        return borrowDAO.getAllBorrows();
    }

    public void returnBook(int borrowId) {
        // Suppression de l'emprunt (ajouter une logique de mise à jour si nécessaire)
        borrowDAO.delete(borrowId);
    }

    public void displayBorrows() {
        List<Borrow> borrows = borrowDAO.getAllBorrows();
        if (borrows.isEmpty()) {
            System.out.println("Aucun emprunt enregistré.");
        } else {
            for (Borrow borrow : borrows) {
                System.out.println("Emprunt ID: " + borrow.getId() +
                        ", Étudiant: " + borrow.getStudent().getName() +
                        ", Livre: " + borrow.getBook().getTitle() +
                        ", Date d'emprunt: " + borrow.getBorrowDate() +
                        ", Date de retour: " + (borrow.getReturnDate() != null ? borrow.getReturnDate() : "Non retourné"));
            }
        }
    }


}