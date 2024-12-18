package com.library;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import com.library.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentDAO studentDAOMock;
    private StudentService studentService;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        studentDAOMock = Mockito.mock(StudentDAO.class);
        studentService = new StudentService(studentDAOMock);
        System.setOut(new PrintStream(outputStreamCaptor));  // Rediriger la sortie vers le ByteArrayOutputStream
    }

    @Test
    void testAddStudent() {
        // Créer un étudiant à ajouter
        Student student = new Student(1, "Alice");

        // Appeler la méthode du service pour ajouter l'étudiant
        studentService.addStudent(student);

        // Vérifier que le DAO a bien été appelé
        verify(studentDAOMock).addStudent(student);
    }

    @Test
    void testDisplayStudents() {
        List<Student> students = Arrays.asList(
                new Student(1, "Alice"),
                new Student(2, "Bob")
        );
        when(studentDAOMock.getAllStudents()).thenReturn(students);

        studentService.displayStudents();

        verify(studentDAOMock, times(1)).getAllStudents();
    }

    @Test
    void testUpdateStudent() {
        // Créer un étudiant à ajouter
        Student student = new Student(1, "Alice");

        // Appeler la méthode du service pour ajouter l'étudiant
        studentService.addStudent(student);

        // Mettre à jour le nom de l'étudiant
        student.setName("Alice Updated");

        // Simuler la mise à jour de l'étudiant
        when(studentDAOMock.getStudentById(1)).thenReturn(student);

        // Appeler la méthode du service pour mettre à jour l'étudiant
        studentService.updateStudent(student);

        // Vérifier que la méthode update du DAO a bien été appelée
        verify(studentDAOMock).updateStudent(student);
    }

    @Test
    void testDeleteStudent() {
        // Créer un étudiant à ajouter
        Student student = new Student(1, "Alice");

        // Simuler l'ajout de l'étudiant
        when(studentDAOMock.getStudentById(1)).thenReturn(student);

        // Appeler la méthode du service pour ajouter l'étudiant
        studentService.addStudent(student);

        // Appeler la méthode pour supprimer l'étudiant
        studentService.deleteStudent(1);

        // Vérifier que la méthode delete du DAO a bien été appelée
        verify(studentDAOMock).deleteStudent(1);
    }
}