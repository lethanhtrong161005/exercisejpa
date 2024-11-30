package com.jpa.exercisejpa.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity(name="course")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="title", nullable=false)
    private String title;
    @Column(name="duration", nullable=false)
    private Double duration;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistrationEntity> registrations;

    @ManyToMany(mappedBy = "courses")
    private List<StudentEntity> students;

    public List<RegistrationEntity> getRegistrations() {
        return registrations;
    }
    public void setRegistrations(List<RegistrationEntity> registrations) {
        this.registrations = registrations;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
