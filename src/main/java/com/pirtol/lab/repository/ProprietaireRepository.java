package com.pirtol.lab.repository;

import com.pirtol.lab.domain.Proprietaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Proprietaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProprietaireRepository extends JpaRepository<Proprietaire, Long> {}
