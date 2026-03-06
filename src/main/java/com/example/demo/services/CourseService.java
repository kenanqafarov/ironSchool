package com.example.demo.services;

import com.example.demo.models.Course;
import com.example.demo.models.Student;
import com.example.demo.models.Teacher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CourseService {

    private final Map<String, Course> courseMap = new HashMap<>();
    private final Map<String, Teacher> teacherMap = new HashMap<>();
    private final Map<String, Student> studentMap = new HashMap<>();

    // Teacher CRUD operations
    public Teacher createTeacher(String name, double salary) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Teacher name cannot be empty");
        }
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        String teacherId = UUID.randomUUID().toString();
        Teacher teacher = new Teacher(name, salary);
        teacher.setTeacherId(teacherId);
        teacherMap.put(teacherId, teacher);
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
        // Remove teacher from any assigned courses
        for (Course course : courseMap.values()) {
            if (course.getTeacher() != null && course.getTeacher().getTeacherId().equals(teacherId)) {
                course.setTeacher(null);
            }
        }
        teacherMap.remove(teacherId);
    }

    // Student CRUD operations
    public Student createStudent(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty");
        }
        String studentId = UUID.randomUUID().toString();
        Student student = new Student(name);
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

    public void updateStudent(String studentId, String newName) {
        Student student = getStudentById(studentId);
        if (newName != null && !newName.isEmpty()) {
            student.setName(newName);
        }
    }

    public void deleteStudent(String studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID cannot be null");
        }
        if (!studentMap.containsKey(studentId)) {
            throw new IllegalArgumentException("Student not found with ID: " + studentId);
        }
        // Remove student from any enrolled courses
        for (Course course : courseMap.values()) {
            course.getStudents().removeIf(s -> s.getStudentId().equals(studentId));
        }
        studentMap.remove(studentId);
    }

    // Course CRUD operations
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

    // Assign teacher to course
    public void assignTeacherToCourse(String courseId, String teacherId) {
        Course course = getCourseById(courseId);
        Teacher teacher = getTeacherById(teacherId);
        course.setTeacher(teacher);
    }

    // Enroll student in course
    public void enrollStudentInCourse(String courseId, String studentId) {
        Course course = getCourseById(courseId);
        Student student = getStudentById(studentId);
        if (!course.getStudents().contains(student)) {
            course.getStudents().add(student);
            course.setMoney_earned(course.getMoney_earned() + course.getPrice());
        } else {
            throw new IllegalStateException("Student is already enrolled in the course");
        }
    }

    // Unenroll student from course (optional, for completeness)
    public void unenrollStudentFromCourse(String courseId, String studentId) {
        Course course = getCourseById(courseId);
        Student student = getStudentById(studentId);
        if (course.getStudents().remove(student)) {
            course.setMoney_earned(course.getMoney_earned() - course.getPrice());
        } else {
            throw new IllegalStateException("Student is not enrolled in the course");
        }
    }

    // Get students enrolled in a course
    public List<Student> getEnrolledStudents(String courseId) {
        Course course = getCourseById(courseId);
        return new ArrayList<>(course.getStudents());
    }

    // Get courses taught by a teacher
    public List<Course> getCoursesByTeacher(String teacherId) {
        getTeacherById(teacherId); // Validate teacher exists
        List<Course> teacherCourses = new ArrayList<>();
        for (Course course : courseMap.values()) {
            if (course.getTeacher() != null && course.getTeacher().getTeacherId().equals(teacherId)) {
                teacherCourses.add(course);
            }
        }
        return teacherCourses;
    }

    // Get courses enrolled by a student (assuming no back-reference, we scan)
    public List<Course> getCoursesForStudent(String studentId) {
        getStudentById(studentId); // Validate student exists
        List<Course> studentCourses = new ArrayList<>();
        for (Course course : courseMap.values()) {
            for (Student s : course.getStudents()) {
                if (s.getStudentId().equals(studentId)) {
                    studentCourses.add(course);
                    break;
                }
            }
        }
        return studentCourses;
    }
}