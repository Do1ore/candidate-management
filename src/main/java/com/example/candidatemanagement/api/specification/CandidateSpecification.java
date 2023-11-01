package com.example.candidatemanagement.api.specification;

import com.example.candidatemanagement.api.model.Candidate;
import org.springframework.data.jpa.domain.Specification;

public class CandidateSpecification {

    public static Specification<Candidate> lastnameLike(String lastName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("last_name")),
                "%" + lastName.toLowerCase() + "%");

    }

    public static Specification<Candidate> firstnameLike(String firstName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("first_name")),
                "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<Candidate> middlenameLike(String middleName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("middle_name")),
                "%" + middleName.toLowerCase() + "%");
    }

    public static Specification<Candidate> descriptionLike(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                "%" + description.toLowerCase() + "%");
    }
}
