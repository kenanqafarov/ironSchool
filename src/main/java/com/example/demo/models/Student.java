package com.example.demo.models;

import jakarta.annotation.Nullable;
//All

public class Student {
    private String studentId;
    private String name;
    private String address;
    private String email;
    @Nullable
    private Course course;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Nullable
    public Course getCourse() {
        return course;
    }

    public void setCourse(@Nullable Course course) {
        this.course = course;
    }

    public Student(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }
}
