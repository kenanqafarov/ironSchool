package com.example.demo.services;

import com.example.demo.models.Student;
import com.example.demo.models.Teacher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
private final List<Student> students=new ArrayList<>();

public Student createStudent(String name, String address, String email){
    Student student=new Student(name, address, email);
    students.add(student);
    return student;
}
public List<Student>getAllstudents(Student student){
    return students;
}


}
