package com.jpa.exercisejpa.specification;

import com.jpa.exercisejpa.entity.CourseEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecification {
    public static Specification<CourseEntity> filterByName(String name) {
        return (Root<CourseEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (name == null || name.isEmpty()) return builder.conjunction();
            return builder.like(root.get("name"), "%" + name + "%");
        };
    }

    public static Specification<CourseEntity> filterByDuration(Integer minDuration, Integer maxDuration) {
        return (Root<CourseEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (minDuration == null && maxDuration == null) return builder.conjunction();
            if (minDuration != null && maxDuration != null) {
                return builder.between(root.get("duration"), minDuration, maxDuration);
            } else if (minDuration != null) {
                return builder.greaterThanOrEqualTo(root.get("duration"), minDuration);
            } else {
                return builder.lessThanOrEqualTo(root.get("duration"), maxDuration);
            }
        };
    }
}
