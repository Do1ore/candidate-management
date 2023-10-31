package com.example.candidatemanagement.api.repository;
import com.example.candidatemanagement.api.model.DirectionTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DirectionTestRepository extends JpaRepository<DirectionTest, UUID>, JpaSpecificationExecutor<DirectionTest> {
}
