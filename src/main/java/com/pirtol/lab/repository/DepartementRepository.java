package com.pirtol.lab.repository;

import com.pirtol.lab.domain.Departement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Departement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long>, JpaSpecificationExecutor<Departement> {}
