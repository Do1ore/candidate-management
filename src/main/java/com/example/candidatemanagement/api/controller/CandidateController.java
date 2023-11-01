package com.example.candidatemanagement.api.controller;

import com.example.candidatemanagement.api.model.Candidate;
import com.example.candidatemanagement.api.model.Direction;
import com.example.candidatemanagement.api.repository.CandidateRepository;
import com.example.candidatemanagement.api.repository.DirectionRepository;
import com.example.candidatemanagement.api.service.FileStorageService;
import com.example.candidatemanagement.api.specification.CandidateSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/candidate")
public class CandidateController {

    private final CandidateRepository candidateRepository;

    private final FileStorageService fileStorageService;
    private final DirectionRepository directionRepository;

    @Autowired
    public CandidateController(CandidateRepository candidateRepository, FileStorageService fileStorageService, DirectionRepository directionRepository) {
        this.candidateRepository = candidateRepository;
        this.fileStorageService = fileStorageService;
        this.directionRepository = directionRepository;
    }

    @GetMapping
    public Page<Candidate> getAllCandidates(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "middleName", required = false) String middleName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "sortParameter", defaultValue = "lastName", required = false) String sortParameter) {

        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(sortParameter));

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

        return candidateRepository.findAll(candidateSpecification, pageable);
    }

    @PostMapping
    public ResponseEntity<Candidate> createCandidate(
            @RequestParam(value = "firstName", required = true) String firstName,
            @RequestParam(value = "lastName", required = true) String lastName,
            @RequestParam(value = "middleName", required = true) String middleName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "photo", required = false) MultipartFile photoFile,
            @RequestParam(value = "directions", required = false) List<UUID> directionIds,
            @RequestParam(value = "cv", required = false) MultipartFile cvFile) {

        Candidate candidate = new Candidate();

        if (firstName.isEmpty() || lastName.isEmpty() || middleName.isEmpty() || description.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        candidate.setFirstName(firstName);
        candidate.setLastName(lastName);
        candidate.setMiddleName(middleName);
        candidate.setDescription(description);

        if (Optional.ofNullable(cvFile).isPresent()) {
            candidate.setCvFile(fileStorageService.storeCv(cvFile));
        }
        if (Optional.ofNullable(photoFile).isPresent()) {
            candidate.setPhoto(fileStorageService.storePhoto(photoFile));
        }

        if (directionIds != null) {
            List<Direction> directions = new ArrayList<>();
            for (var directionId : directionIds) {
                var direction = directionRepository.findById(directionId);
                direction.ifPresent(directions::add);
            }
            candidate.setPossibleDirections(directions);
        }


        return ResponseEntity.ok(candidateRepository.save(candidate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(
            @PathVariable UUID id,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "middleName", required = false) String middleName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "photo", required = false) MultipartFile photoFile,
            @RequestParam(value = "directions", required = false) List<UUID> directionIds,
            @RequestParam(value = "cv", required = false) MultipartFile cvFile) throws IOException {

        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);

        if (optionalCandidate.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Candidate candidate = optionalCandidate.get();

        if (firstName != null) {
            candidate.setFirstName(firstName);
        }
        if (lastName != null) {
            candidate.setLastName(lastName);
        }
        if (middleName != null) {
            candidate.setMiddleName(middleName);
        }
        if (description != null) {
            candidate.setDescription(description);
        }

        if (cvFile != null) {
            candidate.setCvFile(fileStorageService.storeCv(cvFile));
        }
        if (photoFile != null) {
            candidate.setPhoto(fileStorageService.storePhoto(photoFile));
        }

        if (directionIds != null) {
            List<Direction> directions = new ArrayList<>();
            for (UUID directionId : directionIds) {
                Optional<Direction> direction = directionRepository.findById(directionId);
                direction.ifPresent(directions::add);
            }
            candidate.setPossibleDirections(directions);
        }

        return ResponseEntity.ok(candidateRepository.save(candidate));
    }

}
