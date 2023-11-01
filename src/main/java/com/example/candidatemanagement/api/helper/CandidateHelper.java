package com.example.candidatemanagement.api.helper;

import com.example.candidatemanagement.api.model.Candidate;
import com.example.candidatemanagement.api.specification.CandidateSpecification;
import org.springframework.data.jpa.domain.Specification;

public class CandidateHelper {

    public static Specification<Candidate> getCandidateSpecification(String firstName, String lastName, String middleName, String description) {
        Specification<Candidate> candidateSpecification = Specification.where(null);
        if (firstName != null) {
            candidateSpecification = candidateSpecification.and(CandidateSpecification.firstnameLike(firstName));
        }
        if (lastName != null) {
            candidateSpecification = candidateSpecification.and(CandidateSpecification.lastnameLike(lastName));
        }
        if (middleName != null) {
            candidateSpecification = candidateSpecification.and(CandidateSpecification.middlenameLike(middleName));
        }
        if (description != null) {
            candidateSpecification = candidateSpecification.and(CandidateSpecification.descriptionLike(description));
        }
        return candidateSpecification;
    }
}
