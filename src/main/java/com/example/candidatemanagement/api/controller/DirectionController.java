package com.example.candidatemanagement.api.controller;

import com.example.candidatemanagement.api.model.Direction;
import com.example.candidatemanagement.api.repository.DirectionRepository;
import com.example.candidatemanagement.api.helper.DirectionSpecificationHelper;
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
 * Controller to manage Direction entity
 *
 * @version 1.0
 * @// TODO: 01.11.2023 cleanup controller
 */
@RestController
@RequestMapping("/api/v1/direction")
public class DirectionController {

    private final DirectionRepository directionRepository;

    @Autowired
    public DirectionController(DirectionRepository directionRepository) {
        this.directionRepository = directionRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Direction>> getDirection(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "-1") int n0,
            @RequestParam(defaultValue = "-1") int n,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "sortProperty", defaultValue = "candidate.lastName") String sortProperty,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortProperty);


            Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Direction> directionSpecification = Specification.where(null);

        directionSpecification = DirectionSpecificationHelper.getDirectionSpecification(name, description, n0, n, directionSpecification);

        Page<Direction> directions = directionRepository.findAll(directionSpecification, pageable);

        return ResponseEntity.ok(directions);
    }


    @PostMapping
    public ResponseEntity<Direction> createDirection(@RequestBody Direction direction) {
        return ResponseEntity.ok(directionRepository.save(direction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Direction> updateDirection(@PathVariable UUID id, @RequestBody Direction direction) {
        Optional<Direction> directionData = directionRepository.findById(id);

        if (directionData.isPresent()) {
            Direction _direction = directionData.get();
            _direction.setName(direction.getName());
            _direction.setDescription(direction.getDescription());
            return new ResponseEntity<>(directionRepository.save(_direction), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
