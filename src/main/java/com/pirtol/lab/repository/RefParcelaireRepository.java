package com.pirtol.lab.repository;

import com.pirtol.lab.domain.RefParcelaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RefParcelaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefParcelaireRepository extends JpaRepository<RefParcelaire, Long>, JpaSpecificationExecutor<RefParcelaire> {}
