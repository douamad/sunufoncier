package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.EvaluationCoursAmenageDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.EvaluationCoursAmenage}.
 */
public interface EvaluationCoursAmenageService {
    /**
     * Save a evaluationCoursAmenage.
     *
     * @param evaluationCoursAmenageDTO the entity to save.
     * @return the persisted entity.
     */
    EvaluationCoursAmenageDTO save(EvaluationCoursAmenageDTO evaluationCoursAmenageDTO);

    /**
     * Partially updates a evaluationCoursAmenage.
     *
     * @param evaluationCoursAmenageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EvaluationCoursAmenageDTO> partialUpdate(EvaluationCoursAmenageDTO evaluationCoursAmenageDTO);

    /**
     * Get all the evaluationCoursAmenages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvaluationCoursAmenageDTO> findAll(Pageable pageable);

    /**
     * Get the "id" evaluationCoursAmenage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvaluationCoursAmenageDTO> findOne(Long id);

    /**
     * Delete the "id" evaluationCoursAmenage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
