package com.pirtol.lab.repository;

import com.pirtol.lab.domain.UsageDossier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UsageDossier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsageDossierRepository extends JpaRepository<UsageDossier, Long>, JpaSpecificationExecutor<UsageDossier> {}
