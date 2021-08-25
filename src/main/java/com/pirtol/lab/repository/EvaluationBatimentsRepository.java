package com.pirtol.lab.repository;

import com.pirtol.lab.domain.EvaluationBatiments;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EvaluationBatiments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationBatimentsRepository
    extends JpaRepository<EvaluationBatiments, Long>, JpaSpecificationExecutor<EvaluationBatiments> {}
