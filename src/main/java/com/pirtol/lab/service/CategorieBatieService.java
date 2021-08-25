package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.CategorieBatieDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.CategorieBatie}.
 */
public interface CategorieBatieService {
    /**
     * Save a categorieBatie.
     *
     * @param categorieBatieDTO the entity to save.
     * @return the persisted entity.
     */
    CategorieBatieDTO save(CategorieBatieDTO categorieBatieDTO);

    /**
     * Partially updates a categorieBatie.
     *
     * @param categorieBatieDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategorieBatieDTO> partialUpdate(CategorieBatieDTO categorieBatieDTO);

    /**
     * Get all the categorieBaties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategorieBatieDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categorieBatie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategorieBatieDTO> findOne(Long id);

    /**
     * Delete the "id" categorieBatie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
