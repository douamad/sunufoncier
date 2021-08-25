package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.NatureDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.Nature}.
 */
public interface NatureService {
    /**
     * Save a nature.
     *
     * @param natureDTO the entity to save.
     * @return the persisted entity.
     */
    NatureDTO save(NatureDTO natureDTO);

    /**
     * Partially updates a nature.
     *
     * @param natureDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NatureDTO> partialUpdate(NatureDTO natureDTO);

    /**
     * Get all the natures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NatureDTO> findAll(Pageable pageable);

    /**
     * Get the "id" nature.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NatureDTO> findOne(Long id);

    /**
     * Delete the "id" nature.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
