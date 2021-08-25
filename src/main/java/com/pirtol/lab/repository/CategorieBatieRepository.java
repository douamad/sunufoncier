package com.pirtol.lab.repository;

import com.pirtol.lab.domain.CategorieBatie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CategorieBatie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieBatieRepository extends JpaRepository<CategorieBatie, Long>, JpaSpecificationExecutor<CategorieBatie> {}
