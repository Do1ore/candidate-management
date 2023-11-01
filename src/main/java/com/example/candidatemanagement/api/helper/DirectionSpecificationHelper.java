package com.example.candidatemanagement.api.helper;

import com.example.candidatemanagement.api.model.Direction;
import com.example.candidatemanagement.api.specification.DirectionSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class DirectionSpecificationHelper {
    public static Specification<Direction> getDirectionSpecification(String name, String description, int n0, int n, Specification<Direction> directionSpecification) {
        if (Optional.ofNullable(name).isPresent()) {
            directionSpecification = directionSpecification.and(DirectionSpecification.nameLike(name));
        }
        if (Optional.ofNullable(description).isPresent()) {
            directionSpecification = directionSpecification.and(DirectionSpecification.descriptionLike(description));
        }
        if (n0 != -1 && n != -1) {
            directionSpecification = directionSpecification.and(DirectionSpecification.fromN0toN(n0, n));
        }
        return directionSpecification;
    }
}
