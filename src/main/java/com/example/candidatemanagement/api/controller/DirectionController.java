package com.example.candidatemanagement.api.controller;

import com.example.candidatemanagement.api.model.Direction;
import com.example.candidatemanagement.api.repository.DirectionRepository;
import com.example.candidatemanagement.api.specification.DirectionSpecification;
import jakarta.annotation.Nullable;
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
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Specification<Direction> directionSpecification = Specification.where(null);

        if (name != null) {
            directionSpecification = directionSpecification.and(DirectionSpecification.nameLike(name));
        }

        if (description != null) {
            directionSpecification = directionSpecification.and(DirectionSpecification.descriptionLike(description));
        }

        if (n0 != -1 && n != -1) {
            directionSpecification = directionSpecification.and(DirectionSpecification.fromN0toN(n0, n));
        }


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
