package com.jpa.exercisejpa.controller;

import com.jpa.exercisejpa.entity.CourseEntity;
import com.jpa.exercisejpa.services.CourseService;
import com.jpa.exercisejpa.services.RegistrationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.jpa.exercisejpa.dto.CourseDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private ModelMapper modelMapper;
    // ************************************ Câu 2 ************************************//

    @GetMapping
    public ResponseEntity<?> getCoursesWithDurationGreaterThan(@RequestParam(name="durationGreaterThan", required=false) Double hours) {
        if(courseService.getCoursesWithDurationGreaterThan(hours) == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(courseService.getCoursesWithDurationGreaterThan(hours));
    }

    @GetMapping("/count")
    public ResponseEntity<?> countCourses() {
        return ResponseEntity.ok(courseService.countAllCourse());
    }

    // *******************************************************************************//
    // ************************************ Câu 3 ************************************//
    @GetMapping("/{courseId}/students")
    public ResponseEntity<?> getStudentsByCourseId(@PathVariable Integer courseId) {
        CourseEntity course = courseService.findCourseById(courseId);
        if(registrationService.getAllStudentByCourseId(courseId).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registrationService.getAllStudentByCourseId(courseId));
    }
    // *******************************************************************************//
    // ************************************ Câu 5 ************************************//
    @GetMapping("/search")
    public Page<CourseDTO> getCourses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minDuration,
            @RequestParam(required = false) Integer maxDuration,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "duration,desc") String sort) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseEntity> coursePage = courseService.getCourses(name, minDuration, maxDuration, pageable);
        return coursePage.map(courseEntity -> modelMapper.map(courseEntity, CourseDTO.class));
    }


}
