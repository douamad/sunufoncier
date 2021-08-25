package com.pirtol.lab.repository;

import com.pirtol.lab.domain.EvaluationCoursAmenage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EvaluationCoursAmenage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationCoursAmenageRepository
    extends JpaRepository<EvaluationCoursAmenage, Long>, JpaSpecificationExecutor<EvaluationCoursAmenage> {}
