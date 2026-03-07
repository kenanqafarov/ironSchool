package com.example.demo.controllers;

import com.example.demo.models.Course;
import com.example.demo.services.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
private final CourseService courseService;


    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>>getAllCourses(){
        List<Course> allCourses=courseService.getAllCourses();
        return ResponseEntity.ok(allCourses);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course){
     Course newone= courseService.createCourse(course.getName(), course.getPrice());
return ResponseEntity.ok(newone);
    }
@GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable String courseId){
        Course kursId=courseService.getCourseById(courseId);
        return ResponseEntity.ok(kursId);
}
@PutMapping("/{courseId}")
    public ResponseEntity<Void>uptadeCourse(@PathVariable String courseId,@RequestParam String newName,@RequestParam double newPrice){
         courseService.updateCourse(courseId,newName,newPrice);
return ResponseEntity.ok().build();
    }
@DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String courseID){
        courseService.deleteCourse(courseID);
        return ResponseEntity.ok().build();
}
@PostMapping("/{courseId}/assign-teacher/{teacherId}")
    public ResponseEntity<Void>assignTeacherToCourse(@PathVariable String courseId, @PathVariable String teacherId){
    courseService.assignTeacherToCourse(courseId,teacherId);
return ResponseEntity.ok().build();
    }
@GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Course>> getCocursesByTeacher(@PathVariable String teacherId){
        List<Course> teachers=courseService.getCoursesByTeacher(teacherId);
return ResponseEntity.ok(teachers);
    }

}
