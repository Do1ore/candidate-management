package com.example.candidatemanagement.api.specification;

import com.example.candidatemanagement.api.model.Direction;
import com.example.candidatemanagement.api.model.DirectionTest;
import org.springframework.data.jpa.domain.Specification;

public class DirectionTestSpecification {
    public static Specification<DirectionTest> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                "%" + name.toLowerCase() + "%");

    }

    public static Specification<DirectionTest> descriptionLike(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                "%" + description.toLowerCase() + "%");
    }
}
