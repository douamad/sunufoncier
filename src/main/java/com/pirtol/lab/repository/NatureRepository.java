package com.pirtol.lab.repository;

import com.pirtol.lab.domain.Nature;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Nature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureRepository extends JpaRepository<Nature, Long>, JpaSpecificationExecutor<Nature> {}
