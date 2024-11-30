package com.jpa.exercisejpa.services;

import com.jpa.exercisejpa.dto.CourseDTO;
import com.jpa.exercisejpa.dto.StudentDTO;
import com.jpa.exercisejpa.entity.CourseEntity;
import com.jpa.exercisejpa.entity.RegistrationEntity;
import com.jpa.exercisejpa.entity.StudentEntity;
import com.jpa.exercisejpa.repository.RegistrationRepository;
import com.jpa.exercisejpa.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private StudentRepository studentRepository;

    public void addRegistration(RegistrationEntity registration) {
        registrationRepository.save(registration);
    }

    public void removeRegistration(Integer studentId, Integer courseId) {
        boolean exists = registrationRepository.existsByStudentIdAndCourseId(studentId, courseId);
        if(!exists){
            throw new IllegalArgumentException("Registration not found for the given student and course");
        }
        registrationRepository.deleteByStudentIdAndCourseId(studentId, courseId);
    }

    public String isRegisteredForCourse(Integer studentId, Integer courseId){
        if(registrationRepository.findByStudentIdAndCourseId(studentId, courseId) != null){
            return "Student already registered for course";
        }
        return null;
    }

    public List<CourseDTO> getAllCourseByStudentId(Integer studentId){
        return registrationRepository.findCourseByStudentId(studentId).stream().map(
                item ->{
                    CourseDTO courseDTO = new CourseDTO();
                    courseDTO.setId(item.getId());
                    courseDTO.setTitle(item.getTitle());
                    courseDTO.setDuration(item.getDuration());
                    return courseDTO;
                }
        ).toList();
    }

    public List<StudentDTO> getAllStudentByCourseId(Integer courseId){
        return registrationRepository.findStudentByCourseId(courseId).stream().map(
                item -> {
                    StudentDTO studentDTO = new StudentDTO();
                    studentDTO.setId(item.getId());
                    studentDTO.setName(item.getName());
                    studentDTO.setEmail(item.getEmail());
                    studentDTO.setAge(item.getAge());
                    return studentDTO;
                }
        ).toList();
    }

    public RegistrationEntity createRegistration(StudentEntity student, CourseEntity course) {
        RegistrationEntity registration = new RegistrationEntity();
        registration.setStudent(student);
        registration.setCourse(course);
        registration.setRegistrationDate(java.time.LocalDate.now());
        return registration;
    }

}
