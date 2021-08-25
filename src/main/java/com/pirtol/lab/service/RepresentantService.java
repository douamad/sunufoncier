package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.RepresentantDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.Representant}.
 */
public interface RepresentantService {
    /**
     * Save a representant.
     *
     * @param representantDTO the entity to save.
     * @return the persisted entity.
     */
    RepresentantDTO save(RepresentantDTO representantDTO);

    /**
     * Partially updates a representant.
     *
     * @param representantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RepresentantDTO> partialUpdate(RepresentantDTO representantDTO);

    /**
     * Get all the representants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RepresentantDTO> findAll(Pageable pageable);

    /**
     * Get the "id" representant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RepresentantDTO> findOne(Long id);

    /**
     * Delete the "id" representant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
