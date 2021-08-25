package com.pirtol.lab.service;

import com.pirtol.lab.service.dto.EvaluationSurfaceBatieDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.pirtol.lab.domain.EvaluationSurfaceBatie}.
 */
public interface EvaluationSurfaceBatieService {
    /**
     * Save a evaluationSurfaceBatie.
     *
     * @param evaluationSurfaceBatieDTO the entity to save.
     * @return the persisted entity.
     */
    EvaluationSurfaceBatieDTO save(EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO);

    /**
     * Partially updates a evaluationSurfaceBatie.
     *
     * @param evaluationSurfaceBatieDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EvaluationSurfaceBatieDTO> partialUpdate(EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO);

    /**
     * Get all the evaluationSurfaceBaties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EvaluationSurfaceBatieDTO> findAll(Pageable pageable);

    /**
     * Get the "id" evaluationSurfaceBatie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EvaluationSurfaceBatieDTO> findOne(Long id);

    /**
     * Delete the "id" evaluationSurfaceBatie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
