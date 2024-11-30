package com.jpa.exercisejpa.repository;

import com.jpa.exercisejpa.entity.CourseEntity;
import com.jpa.exercisejpa.entity.RegistrationEntity;
import com.jpa.exercisejpa.entity.StudentEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Integer> {
    RegistrationEntity findByStudentIdAndCourseId(Integer studentId, Integer courseId);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM registration r WHERE r.student.id = :studentId AND r.course.id = :courseId")
    boolean existsByStudentIdAndCourseId(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

    @Query("SELECT r.course FROM registration r WHERE r.student.id = :studentId")
    List<CourseEntity> findCourseByStudentId(@Param("studentId") Integer studentId);

    @Query("SELECT r.student FROM registration r WHERE r.course.id = :courseId")
    List<StudentEntity> findStudentByCourseId(@Param("courseId") Integer courseId);

    @Modifying
    @Transactional
    @Query("DELETE FROM registration r WHERE r.student.id = :studentId AND r.course.id = :courseId")
    void deleteByStudentIdAndCourseId(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

}
