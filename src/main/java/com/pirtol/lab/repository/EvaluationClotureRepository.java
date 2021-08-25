package com.pirtol.lab.repository;

import com.pirtol.lab.domain.EvaluationCloture;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EvaluationCloture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationClotureRepository extends JpaRepository<EvaluationCloture, Long>, JpaSpecificationExecutor<EvaluationCloture> {}
