package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.CategorieCoursAmenageDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.CategorieCoursAmenage}.
 */
public interface CategorieCoursAmenageService {
    /**
     * Save a categorieCoursAmenage.
     *
     * @param categorieCoursAmenageDTO the entity to save.
     * @return the persisted entity.
     */
    CategorieCoursAmenageDTO save(CategorieCoursAmenageDTO categorieCoursAmenageDTO);

    /**
     * Partially updates a categorieCoursAmenage.
     *
     * @param categorieCoursAmenageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategorieCoursAmenageDTO> partialUpdate(CategorieCoursAmenageDTO categorieCoursAmenageDTO);

    /**
     * Get all the categorieCoursAmenages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategorieCoursAmenageDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categorieCoursAmenage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategorieCoursAmenageDTO> findOne(Long id);

    /**
     * Delete the "id" categorieCoursAmenage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
