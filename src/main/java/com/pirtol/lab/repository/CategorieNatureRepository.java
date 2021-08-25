package com.pirtol.lab.repository;

import com.pirtol.lab.domain.CategorieNature;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CategorieNature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieNatureRepository extends JpaRepository<CategorieNature, Long>, JpaSpecificationExecutor<CategorieNature> {}
