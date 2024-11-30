package com.jpa.exercisejpa.services;

import com.jpa.exercisejpa.dto.StudentDTO;
import com.jpa.exercisejpa.entity.StudentEntity;
import com.jpa.exercisejpa.exception.FieldRequiredException;
import com.jpa.exercisejpa.repository.StudentRepository;
import com.jpa.exercisejpa.specification.StudentSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ModelMapper modelMapper;

    public StudentEntity createStudent(StudentEntity studentEntity){
        validDate(studentEntity);
        return studentRepository.save(studentEntity);
    }

    public List<StudentDTO> getAllStudents(){
        return studentRepository.findAll().stream().map(
                item ->{
                    StudentDTO studentDTO = new StudentDTO();
                    studentDTO.setId(item.getId());
                    studentDTO.setName(item.getName());
                    studentDTO.setEmail(item.getEmail());
                    studentDTO.setAge(item.getAge());
                    return studentDTO;
                }).toList();
    }

    public StudentDTO getStudentById(int id){
        return studentRepository.findById(id).map(
                item ->{
                    StudentDTO studentDTO = new StudentDTO();
                    studentDTO.setId(item.getId());
                    studentDTO.setName(item.getName());
                    studentDTO.setEmail(item.getEmail());
                    studentDTO.setAge(item.getAge());
                    return studentDTO;
                }).orElse(null);
    }

    public StudentEntity updateStudentById(int id, StudentEntity studentEntity){
        return studentRepository.findById(id).map(student -> {
            validDate(studentEntity);
            student.setName(studentEntity.getName());
            student.setAge(studentEntity.getAge());
            student.setEmail(studentEntity.getEmail());
            return studentRepository.save(student);
        }).orElseThrow(() -> new FieldRequiredException("Student with id " + id + " not found"));
    }

    public boolean deleteStudentById(int id){
        if(studentRepository.existsById(id)){
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<StudentDTO> searchStudentByKeyWordName(String keyword){
        return studentRepository.findByNameContainingIgnoreCase(keyword).stream().map(
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

    public StudentEntity findStudentById(Integer studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
    }

    public void validDate(StudentEntity student){
        if(student.getName() == null || student.getName().equals("")
        || student.getAge() == null
        || student.getEmail() == null || student.getEmail().equals("")){
            throw new FieldRequiredException("Name, email, or age cannot be null or empty");
        }
    }

    public Page<StudentDTO> searchStudents(
            String name,
            Integer ageFrom,
            Integer ageTo,
            String email,
            int page,
            int size,
            String[] sort
    ) {

        Sort sortable = Sort.by(sort[0].equals("id") ? Sort.Order.asc("id") : Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, size, sortable);


        Specification<StudentEntity> spec = Specification.where(StudentSpecification.filterByName(name))
                .and(StudentSpecification.filterByAge(ageFrom, ageTo))
                .and(StudentSpecification.filterByEmail(email));


        Page<StudentEntity> studentPage = studentRepository.findAll(spec, pageable);


        return studentPage.map(student -> modelMapper.map(student, StudentDTO.class));
    }
}
