package com.example.demo.controllers;

import com.example.demo.models.Course;
import com.example.demo.models.Student;
import com.example.demo.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody String name, String address, String email) {
        Student newStudent = studentService.createStudent(name, address, email);
        return ResponseEntity.ok(newStudent);
    }
@GetMapping("/{studentId}")
    public ResponseEntity<Student>getStudentById(@PathVariable String studentId){
        Student studentById=studentService.getStudentById(studentId);
        return ResponseEntity.ok(studentById);
}
    @PutMapping("/{courseId}")
    public ResponseEntity<Void>updateStudent(@RequestBody String studentId, String newName, String newAddress, String newEmail){
        studentService.updateStudent(studentId, newName, newAddress, newEmail);
return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void>deleteStudent(@PathVariable String studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Void>enrollStudentInCourse(@PathVariable String studentId,@PathVariable String courseId) {
        studentService.enrollStudentInCourse(studentId,courseId);
    return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{studentId}/course")
    public ResponseEntity<Void>unenrollStudentFromCourse(@PathVariable String studentId){
    studentService.unenrollStudentFromCourse(studentId);
        return ResponseEntity.ok().build();

    }
    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<Student>>getEnrolledStudents(String courseId) {
    List<Student> studentEnrolled=studentService.getEnrolledStudents(courseId);
    return ResponseEntity.ok(studentEnrolled);
    }
    @GetMapping("/{studentId}/course")
    public ResponseEntity<Course>getCourseForStudent(String studentId) {
        Course courseForStuden=studentService.getCourseForStudent(studentId);
    return ResponseEntity.ok(courseForStuden);
    }





}
