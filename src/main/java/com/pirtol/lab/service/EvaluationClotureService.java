package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.EvaluationClotureDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.EvaluationCloture}.
 */
public interface EvaluationClotureService {
    /**
     * Save a evaluationCloture.
     *
     * @param evaluationClotureDTO the entity to save.
     * @return the persisted entity.
     */
    EvaluationClotureDTO save(EvaluationClotureDTO evaluationClotureDTO);

    /**
     * Partially updates a evaluationCloture.
     *
     * @param evaluationClotureDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EvaluationClotureDTO> partialUpdate(EvaluationClotureDTO evaluationClotureDTO);

    /**
     * Get all the evaluationClotures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvaluationClotureDTO> findAll(Pageable pageable);

    /**
     * Get the "id" evaluationCloture.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvaluationClotureDTO> findOne(Long id);

    /**
     * Delete the "id" evaluationCloture.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
