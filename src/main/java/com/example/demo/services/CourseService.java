package com.example.demo.services;

import com.example.demo.models.Course;
import com.example.demo.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CourseService {

    private final Map<String, Course> courseMap = new HashMap<>();

    @Autowired
    private TeacherService teacherService; // To fetch teachers

    public Course createCourse(String name, double price) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        String courseId = UUID.randomUUID().toString();
        Course course = new Course(name, price);
        course.setCourseId(courseId);
        course.setMoney_earned(0.0);
        courseMap.put(courseId, course);
        return course;
    }

    public Course getCourseById(String courseId) {
        if (courseId == null) {
            throw new IllegalArgumentException("Course ID cannot be null");
        }
        Course course = courseMap.get(courseId);
        if (course == null) {
            throw new IllegalArgumentException("Course not found with ID: " + courseId);
        }
        return course;
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courseMap.values());
    }

    public void updateCourse(String courseId, String newName, double newPrice) {
        Course course = getCourseById(courseId);
        if (newName != null && !newName.isEmpty()) {
            course.setName(newName);
        }
        if (newPrice >= 0) {
            course.setPrice(newPrice);
        }
    }

    public void deleteCourse(String courseId) {
        if (courseId == null) {
            throw new IllegalArgumentException("Course ID cannot be null");
        }
        if (!courseMap.containsKey(courseId)) {
            throw new IllegalArgumentException("Course not found with ID: " + courseId);
        }
        courseMap.remove(courseId);
    }

    public void assignTeacherToCourse(String courseId, String teacherId) {
        Course course = getCourseById(courseId);
        Teacher teacher = teacherService.getTeacherById(teacherId);
        course.setTeacher(teacher);
    }

    public List<Course> getCoursesByTeacher(String teacherId) {
        teacherService.getTeacherById(teacherId); // Validate teacher exists
        List<Course> teacherCourses = new ArrayList<>();
        for (Course course : courseMap.values()) {
            if (course.getTeacher() != null && course.getTeacher().getTeacherId().equals(teacherId)) {
                teacherCourses.add(course);
            }
        }
        return teacherCourses;
    }
}