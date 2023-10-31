package com.example.candidatemanagement.api.controller;

import com.example.candidatemanagement.api.model.DirectionTest;
import com.example.candidatemanagement.api.repository.DirectionTestRepository;
import com.example.candidatemanagement.api.specification.DirectionTestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/direction-test")
public class DirectionTestController {

    private final DirectionTestRepository directionTestRepository;

    @Autowired
    public DirectionTestController(DirectionTestRepository directionTestRepository) {
        this.directionTestRepository = directionTestRepository;
    }

    @GetMapping
    public ResponseEntity<Page<DirectionTest>> getDirectionTest(@RequestParam(required = false) String name,
                                                                @RequestParam(required = false) String description,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Specification<DirectionTest> directionTestSpecification = Specification.where(null);
        if (name != null) {
            directionTestSpecification = directionTestSpecification.and(DirectionTestSpecification.nameLike(name));
        }

        if (description != null) {
            directionTestSpecification = directionTestSpecification.and(DirectionTestSpecification.nameLike(description));
        }

        Page<DirectionTest> directionTests = directionTestRepository.findAll(directionTestSpecification, pageable);
        return ResponseEntity.ok(directionTests);

    }
}
