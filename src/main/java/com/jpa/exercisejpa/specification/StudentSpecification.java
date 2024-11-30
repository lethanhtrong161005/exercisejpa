package com.jpa.exercisejpa.specification;

import com.jpa.exercisejpa.entity.StudentEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<StudentEntity> filterByName(String name) {
        return (Root<StudentEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (name == null || name.isEmpty()) {
                return builder.conjunction();
            }
            return builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }


    public static Specification<StudentEntity> filterByAge(Integer ageFrom, Integer ageTo) {
        return (Root<StudentEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (ageFrom == null && ageTo == null) {
                return builder.conjunction();
            }
            if (ageFrom != null && ageTo != null) {
                return builder.between(root.get("age"), ageFrom, ageTo);
            } else if (ageFrom != null) {
                return builder.greaterThanOrEqualTo(root.get("age"), ageFrom);
            } else {
                return builder.lessThanOrEqualTo(root.get("age"), ageTo);
            }
        };
    }

    public static Specification<StudentEntity> filterByEmail(String emailDomain) {
        return (Root<StudentEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (emailDomain == null || emailDomain.isEmpty()) {
                return builder.conjunction();
            }
            return builder.like(root.get("email"), "%" + emailDomain);
        };
    }
}
