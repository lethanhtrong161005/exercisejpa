package com.jpa.exercisejpa.repository;

import com.jpa.exercisejpa.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer>, JpaSpecificationExecutor<CourseEntity> {
    List<CourseEntity> findByDurationGreaterThan(Double duration);
}
