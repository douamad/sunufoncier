package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.CategorieClotureDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.CategorieCloture}.
 */
public interface CategorieClotureService {
    /**
     * Save a categorieCloture.
     *
     * @param categorieClotureDTO the entity to save.
     * @return the persisted entity.
     */
    CategorieClotureDTO save(CategorieClotureDTO categorieClotureDTO);

    /**
     * Partially updates a categorieCloture.
     *
     * @param categorieClotureDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategorieClotureDTO> partialUpdate(CategorieClotureDTO categorieClotureDTO);

    /**
     * Get all the categorieClotures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategorieClotureDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categorieCloture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategorieClotureDTO> findOne(Long id);

    /**
     * Delete the "id" categorieCloture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
