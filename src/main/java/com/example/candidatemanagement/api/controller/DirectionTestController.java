package com.example.candidatemanagement.api.controller;

import com.example.candidatemanagement.api.model.DirectionTest;
import com.example.candidatemanagement.api.repository.DirectionRepository;
import com.example.candidatemanagement.api.repository.DirectionTestRepository;
import com.example.candidatemanagement.api.helper.DirectionTestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * Controller to manage DirectionTest entity
 * @version 1.0
 *
 * @// TODO: 01.11.2023 cleanup controller
 *
 */
@RestController
@RequestMapping("/api/v1/direction-test")
public class DirectionTestController {

    private final DirectionTestRepository directionTestRepository;
    private final DirectionRepository directionRepository;

    @Autowired
    public DirectionTestController(DirectionTestRepository directionTestRepository,
                                   DirectionRepository directionRepository) {
        this.directionTestRepository = directionTestRepository;
        this.directionRepository = directionRepository;
    }

    @GetMapping
    public ResponseEntity<Page<DirectionTest>> getDirectionTest(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Specification<DirectionTest> directionTestSpecification = DirectionTestHelper.getDirectionTestSpecification(name, description);

        Page<DirectionTest> directionTests = directionTestRepository.findAll(directionTestSpecification, pageable);
        return ResponseEntity.ok(directionTests);

    }


    @PostMapping
    public ResponseEntity<DirectionTest> createTestDirection(@RequestBody DirectionTest directionTest) {

        if (directionTest.getApplicableDirections() != null) {
            directionRepository.saveAll(directionTest.getApplicableDirections());
        }

        return ResponseEntity.ok(directionTestRepository.save(directionTest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirectionTest> updateTestDirection(@PathVariable UUID id, @RequestBody DirectionTest directionTest) {
        Optional<DirectionTest> directionTestData = directionTestRepository.findById(id);

        if (directionTestData.isPresent()) {
            DirectionTest _directionTest = directionTestData.get();
            _directionTest.setName(directionTest.getName());
            _directionTest.setDescription(directionTest.getDescription());
            return new ResponseEntity<>(directionTestRepository.save(_directionTest), HttpStatus.OK);
        }

        return ResponseEntity.ok(directionTestRepository.save(directionTest));
    }
}
