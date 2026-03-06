package com.example.demo.services;

import com.example.demo.models.Teacher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TeacherService {

    private final Map<String, Teacher> teacherMap = new HashMap<>();

    public Teacher createTeacher(String name, double salary) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Teacher name cannot be empty");
        }
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        Teacher teacher = new Teacher(name, salary);
        teacherMap.put(teacher.getTeacherId(), teacher);
        return teacher;
    }

    public Teacher getTeacherById(String teacherId) {
        if (teacherId == null) {
            throw new IllegalArgumentException("Teacher ID cannot be null");
        }
        Teacher teacher = teacherMap.get(teacherId);
        if (teacher == null) {
            throw new IllegalArgumentException("Teacher not found with ID: " + teacherId);
        }
        return teacher;
    }

    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(teacherMap.values());
    }

    public void updateTeacher(String teacherId, String newName, double newSalary) {
        Teacher teacher = getTeacherById(teacherId);
        if (newName != null && !newName.isEmpty()) {
            teacher.setName(newName);
        }
        if (newSalary >= 0) {
            teacher.setSalary(newSalary);
        }
    }

    public void deleteTeacher(String teacherId) {
        if (teacherId == null) {
            throw new IllegalArgumentException("Teacher ID cannot be null");
        }
        if (!teacherMap.containsKey(teacherId)) {
            throw new IllegalArgumentException("Teacher not found with ID: " + teacherId);
        }
        teacherMap.remove(teacherId);
    }
}