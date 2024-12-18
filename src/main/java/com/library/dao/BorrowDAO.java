package com.library.dao;

import com.library.model.Borrow;
import com.library.model.Student;
import com.library.model.Book;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowDAO {

    public List<Borrow> getAllBorrows() {
        List<Borrow> borrows = new ArrayList<>();
        String query = "SELECT b.id, b.borrow_date, b.return_date, s.id AS student_id, s.name AS student_name, "
                + "bk.id AS book_id, bk.title AS book_title "
                + "FROM borrows b "
                + "JOIN students s ON b.student_id = s.id "
                + "JOIN books bk ON b.book_id = bk.id";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Obtenir les informations de l'étudiant à partir du ResultSet
                Student student = new Student();
                student.setId(rs.getInt("student_id"));
                student.setName(rs.getString("student_name"));

                // Obtenir les informations du livre à partir du ResultSet
                Book book = new Book();
                book.setId(rs.getInt("book_id"));
                book.setTitle(rs.getString("book_title"));

                // Créer l'objet Borrow et l'ajouter à la liste
                Borrow borrow = new Borrow(
                        rs.getInt("id"),
                        student,
                        book,
                        rs.getDate("borrow_date"),
                        rs.getDate("return_date")
                );

                borrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrows;
    }

    public void save(Borrow borrow) {
        String query = "INSERT INTO borrows (student_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, borrow.getStudent().getId());
            stmt.setInt(2, borrow.getBook().getId());
            stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(4, borrow.getReturnDate() != null ? new java.sql.Date(borrow.getReturnDate().getTime()) : null);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM borrows WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}