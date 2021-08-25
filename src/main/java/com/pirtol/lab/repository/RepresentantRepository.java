package com.pirtol.lab.repository;

import com.pirtol.lab.domain.Representant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Representant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RepresentantRepository extends JpaRepository<Representant, Long> {}
