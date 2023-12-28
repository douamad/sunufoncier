package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.EvaluationBatimentsDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.EvaluationBatiments}.
 */
public interface EvaluationBatimentsService {
    /**
     * Save a evaluationBatiments.
     *
     * @param evaluationBatimentsDTO the entity to save.
     * @return the persisted entity.
     */
    EvaluationBatimentsDTO save(EvaluationBatimentsDTO evaluationBatimentsDTO);

    List<EvaluationBatimentsDTO> saveBulk(List<EvaluationBatimentsDTO> evaluationBatimentsDTOList);

    /**
     * Partially updates a evaluationBatiments.
     *
     * @param evaluationBatimentsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EvaluationBatimentsDTO> partialUpdate(EvaluationBatimentsDTO evaluationBatimentsDTO);

    /**
     * Get all the evaluationBatiments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvaluationBatimentsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" evaluationBatiments.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvaluationBatimentsDTO> findOne(Long id);

    /**
     * Delete the "id" evaluationBatiments.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
