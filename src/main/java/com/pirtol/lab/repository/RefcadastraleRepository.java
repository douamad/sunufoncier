package com.pirtol.lab.repository;

import com.pirtol.lab.domain.Refcadastrale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Refcadastrale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefcadastraleRepository extends JpaRepository<Refcadastrale, Long>, JpaSpecificationExecutor<Refcadastrale> {}
