package com.pirtol.lab.repository;

import com.pirtol.lab.domain.Commune;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Commune entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long>, JpaSpecificationExecutor<Commune> {}
