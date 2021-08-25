package com.pirtol.lab.repository;

import com.pirtol.lab.domain.EvaluationSurfaceBatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EvaluationSurfaceBatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationSurfaceBatieRepository
    extends JpaRepository<EvaluationSurfaceBatie, Long>, JpaSpecificationExecutor<EvaluationSurfaceBatie> {}
