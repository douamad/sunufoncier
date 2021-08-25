package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.ArrondissementDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.Arrondissement}.
 */
public interface ArrondissementService {
    /**
     * Save a arrondissement.
     *
     * @param arrondissementDTO the entity to save.
     * @return the persisted entity.
     */
    ArrondissementDTO save(ArrondissementDTO arrondissementDTO);

    /**
     * Partially updates a arrondissement.
     *
     * @param arrondissementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArrondissementDTO> partialUpdate(ArrondissementDTO arrondissementDTO);

    /**
     * Get all the arrondissements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArrondissementDTO> findAll(Pageable pageable);

    /**
     * Get the "id" arrondissement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArrondissementDTO> findOne(Long id);

    /**
     * Delete the "id" arrondissement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
