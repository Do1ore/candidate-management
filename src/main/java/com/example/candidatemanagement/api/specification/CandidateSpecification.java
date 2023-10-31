package com.example.candidatemanagement.api.specification;

import com.example.candidatemanagement.api.model.Candidate;
import org.springframework.data.jpa.domain.Specification;

public class CandidateSpecification {

    public static Specification<Candidate> lastnameLike(String lastName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
                "%" + lastName.toLowerCase() + "%");

    }

    public static Specification<Candidate> firstnameLike(String first_name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("first_name")),
                "%" + first_name.toLowerCase() + "%");
    }

    public static Specification<Candidate> middlenameLike(String middle_name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("middle_name")),
                "%" + middle_name.toLowerCase() + "%");
    }

    public static Specification<Candidate> descriptionLike(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                "%" + description.toLowerCase() + "%");
    }
}
