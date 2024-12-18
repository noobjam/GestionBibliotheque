package com.library.dao;

import com.library.model.Student;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // Ajouter un étudiant
    public void addStudent(Student student) {
        String query = "INSERT INTO students (id,name) VALUES (?,?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, student.getId());
            statement.setString(2, student.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Récupérer un étudiant par ID
    public Student getStudentById(int id) {
        String query = "SELECT * FROM students WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Student(resultSet.getInt("id"),resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Récupérer tous les étudiants
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT id, name FROM students";  // Selecting both 'id' and 'name'

        try (Connection connection = DbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                // Fetch 'id' and 'name' from the result set
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                // Create the student using the id and name
                students.add(new Student(id,name));  // Passing both id and name to the constructor
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }






    // Mettre à jour un étudiant
    public void updateStudent(Student student) {
        String query = "UPDATE students SET name = ? WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, student.getName());
            statement.setInt(2, student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supprimer un étudiant
    public void deleteStudent(int id) {
        String query = "DELETE FROM students WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}