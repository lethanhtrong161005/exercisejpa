package com.jpa.exercisejpa.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity(name="student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name", nullable=false)
    private String name;
    @Column(name="email", nullable=false)
    private String email;
    @Column(name="age", nullable=false)
    private Integer age;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistrationEntity> registrations;

    @ManyToMany
    @JoinTable(
            name = "registration",
            joinColumns = @JoinColumn(name="student_id"),
            inverseJoinColumns = @JoinColumn(name="course_id")
    )
    private List<CourseEntity> courses;

    public List<CourseEntity> getCourses() {
        return courses;
    }
    public void setCourses(List<CourseEntity> courses) {
        this.courses = courses;
    }

    public List<RegistrationEntity> getRegistrations() {
        return registrations;
    }
    public void setRegistrations(List<RegistrationEntity> registrations) {
        this.registrations = registrations;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
