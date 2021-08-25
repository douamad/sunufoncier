package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.UsageDossierDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.UsageDossier}.
 */
public interface UsageDossierService {
    /**
     * Save a usageDossier.
     *
     * @param usageDossierDTO the entity to save.
     * @return the persisted entity.
     */
    UsageDossierDTO save(UsageDossierDTO usageDossierDTO);

    /**
     * Partially updates a usageDossier.
     *
     * @param usageDossierDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UsageDossierDTO> partialUpdate(UsageDossierDTO usageDossierDTO);

    /**
     * Get all the usageDossiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UsageDossierDTO> findAll(Pageable pageable);

    /**
     * Get the "id" usageDossier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UsageDossierDTO> findOne(Long id);

    /**
     * Delete the "id" usageDossier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
