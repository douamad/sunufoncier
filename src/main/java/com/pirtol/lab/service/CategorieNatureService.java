package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.CategorieNatureDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.CategorieNature}.
 */
public interface CategorieNatureService {
    /**
     * Save a categorieNature.
     *
     * @param categorieNatureDTO the entity to save.
     * @return the persisted entity.
     */
    CategorieNatureDTO save(CategorieNatureDTO categorieNatureDTO);

    /**
     * Partially updates a categorieNature.
     *
     * @param categorieNatureDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategorieNatureDTO> partialUpdate(CategorieNatureDTO categorieNatureDTO);

    /**
     * Get all the categorieNatures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategorieNatureDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categorieNature.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategorieNatureDTO> findOne(Long id);

    /**
     * Delete the "id" categorieNature.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
