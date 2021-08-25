package com.pirtol.lab.repository;

import com.pirtol.lab.domain.Locataire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Locataire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocataireRepository extends JpaRepository<Locataire, Long>, JpaSpecificationExecutor<Locataire> {}
