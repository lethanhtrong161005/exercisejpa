package com.jpa.exercisejpa.controller;


import com.jpa.exercisejpa.entity.CourseEntity;
import com.jpa.exercisejpa.entity.RegistrationEntity;
import com.jpa.exercisejpa.entity.StudentEntity;
import com.jpa.exercisejpa.services.CourseService;
import com.jpa.exercisejpa.services.RegistrationService;
import com.jpa.exercisejpa.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jpa.exercisejpa.dto.StudentDTO;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private RegistrationService registrationService;

   // ************************************ Câu 1 ************************************//

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentEntity student) {
        StudentEntity createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        if(studentService.getAllStudents().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Integer id) {
        if(studentService.getStudentById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Integer id, @RequestBody StudentEntity student) {
        StudentEntity updatedStudent = studentService.updateStudentById(id, student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
        if(studentService.deleteStudentById(id)){
            return ResponseEntity.ok("Deleted student with id " + id);
        }
        return ResponseEntity.notFound().build();
    }

    // *******************************************************************************//

    // ************************************ Câu 2 ************************************//

    @GetMapping("/search")
    public ResponseEntity<?> searchStudentByKeyWordName(@RequestParam(name="name", required=false) String keyword) {
        if(studentService.searchStudentByKeyWordName(keyword) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.searchStudentByKeyWordName(keyword));
    }

    // *******************************************************************************//

    // ************************************ Câu 3 ************************************//
    @PostMapping("/{studentId}/courses")
    public ResponseEntity<?> addCoursesToStudent(@PathVariable Integer studentId, @RequestBody List<Integer> courseIds) {
        StudentEntity student = studentService.findStudentById(studentId);
        for(Integer courseId : courseIds) {
            if(registrationService.isRegisteredForCourse(studentId, courseId) != null){
                return ResponseEntity.badRequest().body(registrationService.isRegisteredForCourse(studentId, courseId));
            }else{
                CourseEntity course = courseService.findCourseById(courseId);
                RegistrationEntity registration = registrationService.createRegistration(student, course);
                registrationService.addRegistration(registration);
            }
        }
        return ResponseEntity.ok("Courses added successfully");
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<?> getCoursesOfStudent(@PathVariable Integer studentId) {
        StudentEntity student = studentService.findStudentById(studentId);
        if(registrationService.getAllCourseByStudentId(studentId).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registrationService.getAllCourseByStudentId(studentId));
    }
    // *******************************************************************************//

    // ************************************ Câu 4 ************************************//
    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<?> addStudentToCourse(@PathVariable Integer studentId, @PathVariable Integer courseId) {
            StudentEntity student = studentService.findStudentById(studentId);
            CourseEntity course = courseService.findCourseById(courseId);
            if(registrationService.isRegisteredForCourse(studentId, courseId) != null){
                return ResponseEntity.badRequest().body(registrationService.isRegisteredForCourse(studentId, courseId));
            }
            RegistrationEntity registration = registrationService.createRegistration(student, course);
            registrationService.addRegistration(registration);
            return ResponseEntity.ok("Student added successfully");
    }

    @DeleteMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<?> deleteStudentFromCourse(@PathVariable Integer studentId, @PathVariable Integer courseId) {
        registrationService.removeRegistration(studentId, courseId);
        return ResponseEntity.ok("Student deleted successfully");
    }

    /*
       	GET /api/courses/{courseId}/students: Lấy danh sách tất cả sinh viên trong một khóa học.
       	GET /api/students/{studentId}/courses: Lấy danh sách các khóa học mà sinh viên tham gia.
     */

    // *******************************************************************************//

    // ************************************ Câu 5 ************************************//
    @GetMapping("/searchs")
    public Page<StudentDTO> searchStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer ageFrom,
            @RequestParam(required = false) Integer ageTo,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        return studentService.searchStudents(name, ageFrom, ageTo, email, page, size, sort);
    }
}
