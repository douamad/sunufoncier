package com.pirtol.lab.repository;

import com.pirtol.lab.domain.Arrondissement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Arrondissement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArrondissementRepository extends JpaRepository<Arrondissement, Long>, JpaSpecificationExecutor<Arrondissement> {}
