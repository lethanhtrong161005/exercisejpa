package com.jpa.exercisejpa.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name="registration")
public class RegistrationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="student_id", nullable = false)
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name="course_id", nullable = false)
    private CourseEntity course;

    @Column(name="registration_date")
    private LocalDate registrationDate;

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }
}
