package com.example.candidatemanagement.api.repository;

import com.example.candidatemanagement.api.model.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, UUID>, JpaSpecificationExecutor<Direction> {
}