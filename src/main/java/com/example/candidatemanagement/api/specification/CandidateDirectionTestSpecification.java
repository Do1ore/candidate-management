package com.example.candidatemanagement.api.specification;

import com.example.candidatemanagement.api.model.Candidate;
import com.example.candidatemanagement.api.model.CandidateDirectionTest;
import org.springframework.data.jpa.domain.Specification;

public class CandidateDirectionTestSpecification {
    public static Specification<CandidateDirectionTest> candidateLastNameLike(String candidateLastName) {

        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("candidate").get("lastName")),
                        "%" + candidateLastName.toLowerCase() + "%");
    }

    public static Specification<CandidateDirectionTest> candidateFirstNameLike(String candidateFirstName) {

        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("candidate").get("firstName")),
                        "%" + candidateFirstName.toLowerCase() + "%");
    }

    public static Specification<CandidateDirectionTest> resultHistoryScoreLike(int score) {

        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get("resultHistory").get("score"),
                        "%" + score + "%");
    }
}
