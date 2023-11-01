package com.example.candidatemanagement.api.controller;

import com.example.candidatemanagement.api.model.Candidate;
import com.example.candidatemanagement.api.model.Direction;
import com.example.candidatemanagement.api.repository.CandidateRepository;
import com.example.candidatemanagement.api.repository.DirectionRepository;
import com.example.candidatemanagement.api.helper.CandidateHelper;
import com.example.candidatemanagement.api.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Controller to manage Candidate entity
 * @version 1.0
 *
 * @// TODO: 01.11.2023 cleanup controller
 *
 */
@RestController
@RequestMapping("/api/v1/candidate")
public class CandidateController {

    Logger logger = LoggerFactory.getLogger(CandidateController.class);
    private final CandidateRepository candidateRepository;

    private final FileStorageService fileStorageService;

    private final DirectionRepository directionRepository;

    @Autowired
    public CandidateController(CandidateRepository candidateRepository, FileStorageService fileStorageService, DirectionRepository directionRepository) {
        this.candidateRepository = candidateRepository;
        this.fileStorageService = fileStorageService;
        this.directionRepository = directionRepository;
    }

    /**
     * Comment example
     * Method to access a list of candidates with optional filtering and sorting.
     *
     * @param firstName     The candidate's first name. Optional parameter.
     * @param lastName      The candidate's last name. Optional parameter.
     * @param middleName    The candidate's middle name. Optional parameter.
     * @param description   The candidate's description. Optional parameter.
     * @param page          The page number for pagination. Defaults to 0.
     * @param size          The page size for pagination. Defaults to 10.
     * @param sortParameter The parameter for sorting. Defaults to sorting by last name.
     * @return A page of candidates that match the specified parameters.
     */
    @GetMapping
    public Page<Candidate> getAllCandidates(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "middleName", required = false) String middleName,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(value = "sortParameter", defaultValue = "lastName", required = false) String sortParameter) {

        logger.info("Getting all candidates with parameters: firstName={}, lastName={}, middleName={}, description={}, page={}, size={}, sortParameter={}",
                firstName, lastName, middleName, description, page, size, sortParameter);

        Pageable pageable = PageRequest.of(page, size).withSort(Sort.by(sortParameter));

        Specification<Candidate> candidateSpecification = CandidateHelper.getCandidateSpecification(firstName, lastName, middleName, description);

        var candidates = candidateRepository.findAll(candidateSpecification, pageable);

        logger.info("Found {} candidates", candidates.getTotalElements());

        return candidates;
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
        logger.info("Creating candidate record...");

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
        var createdData = candidateRepository.save(candidate);
        logger.info("Candidate created: {}", createdData.getId());
        return ResponseEntity.ok(createdData);
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
        var result = ResponseEntity.ok(candidateRepository.save(candidate));
        logger.info("Creating candidate record...");
        return result;
    }

}
