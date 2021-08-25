package com.pirtol.lab.repository;

import com.pirtol.lab.domain.Quartier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Quartier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuartierRepository extends JpaRepository<Quartier, Long>, JpaSpecificationExecutor<Quartier> {}
