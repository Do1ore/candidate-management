package com.example.candidatemanagement.api.controller;

import com.example.candidatemanagement.api.model.Candidate;
import com.example.candidatemanagement.api.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/candidate")
public class CandidateController {

    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateController(CandidateRepository activityRepository) {
        this.candidateRepository = activityRepository;
    }

    @GetMapping
    public Candidate getCandidate(@RequestParam UUID candidateId) {
        Optional<Candidate> candidate = Optional.empty();

        return candidateRepository.findById(candidateId).orElse(new Candidate());

    }

    @PostMapping
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        return candidateRepository.save(candidate);
    }
}
