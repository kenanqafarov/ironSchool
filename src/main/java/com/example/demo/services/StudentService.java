package com.example.demo.services;

import com.example.demo.models.Course;
import com.example.demo.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class StudentService {

    private final Map<String, Student> studentMap = new HashMap<>();

    @Autowired
    private CourseService courseService; // To fetch courses and update money_earned

    public Student createStudent(String name, String address, String email) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty");
        }
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Student address cannot be empty");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Student email cannot be empty");
        }
        String studentId = UUID.randomUUID().toString();
        Student student = new Student(name, address, email);
        student.setStudentId(studentId);
        studentMap.put(studentId, student);
        return student;
    }

    public Student getStudentById(String studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        Student student = studentMap.get(studentId);
        if (student == null) {
            throw new IllegalArgumentException("Student not found with ID: " + studentId);
        }
        return student;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(studentMap.values());
    }

    public void updateStudent(String studentId, String newName, String newAddress, String newEmail) {
        Student student = getStudentById(studentId);
        if (newName != null && !newName.isEmpty()) {
            student.setName(newName);
        }
        if (newAddress != null && !newAddress.isEmpty()) {
            student.setAddress(newAddress);
        }
        if (newEmail != null && !newEmail.isEmpty()) {
            student.setEmail(newEmail);
        }
    }

    public void deleteStudent(String studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        if (!studentMap.containsKey(studentId)) {
            throw new IllegalArgumentException("Student not found with ID: " + studentId);
        }
        Student student = getStudentById(studentId);
        unenrollStudentFromCourse(studentId);
        studentMap.remove(studentId);
    }

    public void enrollStudentInCourse(String studentId, String courseId) {
        Student student = getStudentById(studentId);
        Course newCourse = courseService.getCourseById(courseId);
        Course oldCourse = student.getCourse();
        if (oldCourse != null) {
            oldCourse.setMoney_earned(oldCourse.getMoney_earned() - oldCourse.getPrice());
        }
        student.setCourse(newCourse);
        newCourse.setMoney_earned(newCourse.getMoney_earned() + newCourse.getPrice());
    }

    public void unenrollStudentFromCourse(String studentId) {
        Student student = getStudentById(studentId);
        Course course = student.getCourse();
        if (course != null) {
            course.setMoney_earned(course.getMoney_earned() - course.getPrice());
            student.setCourse(null);
        } else {
            throw new IllegalStateException("Student is not enrolled in any course");
        }
    }

    public List<Student> getEnrolledStudents(String courseId) {
        courseService.getCourseById(courseId); // Validate course exists
        List<Student> enrolledStudents = new ArrayList<>();
        for (Student student : studentMap.values()) {
            if (student.getCourse() != null && student.getCourse().getCourseId().equals(courseId)) {
                enrolledStudents.add(student);
            }
        }
        return enrolledStudents;
    }

    public Course getCourseForStudent(String studentId) {
        Student student = getStudentById(studentId);
        return student.getCourse();
    }
}