package com.pirtol.lab.repository;

import com.pirtol.lab.domain.CategorieCoursAmenage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CategorieCoursAmenage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieCoursAmenageRepository
    extends JpaRepository<CategorieCoursAmenage, Long>, JpaSpecificationExecutor<CategorieCoursAmenage> {}
