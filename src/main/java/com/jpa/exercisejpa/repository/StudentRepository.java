package com.jpa.exercisejpa.repository;

import com.jpa.exercisejpa.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer>, JpaSpecificationExecutor<StudentEntity> {
    List<StudentEntity> findByNameContainingIgnoreCase(String name);
}
