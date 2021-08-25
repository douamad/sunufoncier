package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.DossierDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.Dossier}.
 */
public interface DossierService {
    /**
     * Save a dossier.
     *
     * @param dossierDTO the entity to save.
     * @return the persisted entity.
     */
    DossierDTO save(DossierDTO dossierDTO);

    /**
     * Partially updates a dossier.
     *
     * @param dossierDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DossierDTO> partialUpdate(DossierDTO dossierDTO);

    /**
     * Get all the dossiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DossierDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dossier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DossierDTO> findOne(Long id);

    /**
     * Delete the "id" dossier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
