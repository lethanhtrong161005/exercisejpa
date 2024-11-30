package com.jpa.exercisejpa.services;

import com.jpa.exercisejpa.dto.CourseDTO;
import com.jpa.exercisejpa.entity.CourseEntity;
import com.jpa.exercisejpa.repository.CourseRepository;
import com.jpa.exercisejpa.specification.CourseSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;


    public List<CourseDTO> getCoursesWithDurationGreaterThan(Double duration) {
        return courseRepository.findByDurationGreaterThan(duration).stream().map(
                item -> {
                    CourseDTO courseDTO = new CourseDTO();
                    courseDTO.setId(item.getId());
                    courseDTO.setTitle(item.getTitle());
                    courseDTO.setDuration(item.getDuration());
                    return courseDTO;
                }).toList();
    }

    public long countAllCourse(){
        return courseRepository.count();
    }

    public CourseEntity findCourseById(Integer courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
    }

    public Page<CourseEntity> getCourses(String name, Integer minDuration, Integer maxDuration, Pageable pageable) {
        Specification<CourseEntity> spec = Specification.where(CourseSpecification.filterByName(name))
                .and(CourseSpecification.filterByDuration(minDuration, maxDuration));
        return courseRepository.findAll(spec, pageable);
    }
}
