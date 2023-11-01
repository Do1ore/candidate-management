package com.example.candidatemanagement.api.helper;

import com.example.candidatemanagement.api.model.DirectionTest;
import com.example.candidatemanagement.api.specification.DirectionTestSpecification;
import org.springframework.data.jpa.domain.Specification;

public class DirectionTestHelper {

    public static Specification<DirectionTest> getDirectionTestSpecification(String name, String description) {
        Specification<DirectionTest> directionTestSpecification = Specification.where(null);
        if (name != null) {
            directionTestSpecification = directionTestSpecification.and(DirectionTestSpecification.nameLike(name));
        }

        if (description != null) {
            directionTestSpecification = directionTestSpecification.and(DirectionTestSpecification.descriptionLike(description));
        }
        return directionTestSpecification;
    }
}
