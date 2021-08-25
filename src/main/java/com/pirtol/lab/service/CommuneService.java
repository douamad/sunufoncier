package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.CommuneDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.Commune}.
 */
public interface CommuneService {
    /**
     * Save a commune.
     *
     * @param communeDTO the entity to save.
     * @return the persisted entity.
     */
    CommuneDTO save(CommuneDTO communeDTO);

    /**
     * Partially updates a commune.
     *
     * @param communeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommuneDTO> partialUpdate(CommuneDTO communeDTO);

    /**
     * Get all the communes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CommuneDTO> findAll(Pageable pageable);

    /**
     * Get the "id" commune.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommuneDTO> findOne(Long id);

    /**
     * Delete the "id" commune.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
