package com.example.candidatemanagement.api.controller;

import com.example.candidatemanagement.api.model.CandidateDirectionTest;
import com.example.candidatemanagement.api.repository.CandidateDirectionTestRepository;
import com.example.candidatemanagement.api.specification.CandidateDirectionTestSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * Controller to manage CandidateDirectionTest entity
 * @version 1.0
 *
 * @// TODO: 01.11.2023 cleanup controller
 *
 */
@RestController
@RequestMapping("/api/v1/candidate-test")
public class CandidateDirectionTestController {

    private final CandidateDirectionTestRepository candidateDirectionRepository;

    @Autowired
    public CandidateDirectionTestController(CandidateDirectionTestRepository candidateDirectionRepository) {
        this.candidateDirectionRepository = candidateDirectionRepository;
    }


    @GetMapping
    public ResponseEntity<Page<CandidateDirectionTest>> getDirection(
            @RequestParam(value = "firstName", required = false) String candidateFirstName,
            @RequestParam(value = "lastName", required = false) String candidateLastName,
            @RequestParam(value = "score", defaultValue = "-1", required = false) int score,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortProperty", defaultValue = "candidate.lastName") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortProperty);
        Pageable pageable = PageRequest.of(page, size, sort);


        Specification<CandidateDirectionTest> directionTestSpecification = Specification.where(null);

        if (candidateLastName != null) {
            directionTestSpecification = directionTestSpecification
                    .and(CandidateDirectionTestSpecification.candidateLastNameLike(candidateLastName));
        }

        if (candidateFirstName != null) {
            directionTestSpecification = directionTestSpecification.and(CandidateDirectionTestSpecification
                    .candidateFirstNameLike(candidateFirstName));
        }

        if (score != -1) {
            directionTestSpecification = directionTestSpecification.and(CandidateDirectionTestSpecification
                    .resultHistoryScoreLike(score));
        }


        Page<CandidateDirectionTest> result = candidateDirectionRepository.findAll(directionTestSpecification, pageable);
        return ResponseEntity.ok(result);
    }


    @PostMapping()
    public ResponseEntity<CandidateDirectionTest> createDirection(@RequestBody CandidateDirectionTest candidateDirectionTest) {
        var result = candidateDirectionRepository.save(candidateDirectionTest);


        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDirectionTest> updateCandidateDirectionTest(
            @PathVariable UUID id,
            @RequestBody CandidateDirectionTest updatedCandidateDirectionTest
    ) {
        Optional<CandidateDirectionTest> optionalCandidateDirectionTest = candidateDirectionRepository.findById(id);

        if (optionalCandidateDirectionTest.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var existingEntity = optionalCandidateDirectionTest.get();

        existingEntity.setCandidate(updatedCandidateDirectionTest.getCandidate());
        existingEntity.setTest(updatedCandidateDirectionTest.getTest());

        existingEntity.getResultHistories().clear();
        existingEntity.getResultHistories().addAll(updatedCandidateDirectionTest.getResultHistories());

        var result = candidateDirectionRepository.save(existingEntity);


        return ResponseEntity.ok(result);
    }

}
