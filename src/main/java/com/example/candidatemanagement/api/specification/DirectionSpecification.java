package com.example.candidatemanagement.api.specification;

import com.example.candidatemanagement.api.model.Direction;
import org.springframework.data.jpa.domain.Specification;

public class DirectionSpecification {

    public static Specification<Direction> nameLike(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                "%" + name.toLowerCase() + "%");

    }

    public static Specification<Direction> descriptionLike(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                "%" + description.toLowerCase() + "%");
    }
}
