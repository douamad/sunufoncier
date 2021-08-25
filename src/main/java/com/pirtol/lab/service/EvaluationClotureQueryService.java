package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.EvaluationCloture;
import com.pirtol.lab.repository.EvaluationClotureRepository;
import com.pirtol.lab.service.criteria.EvaluationClotureCriteria;
import com.pirtol.lab.service.dto.EvaluationClotureDTO;
import com.pirtol.lab.service.mapper.EvaluationClotureMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link EvaluationCloture} entities in the database.
 * The main input is a {@link EvaluationClotureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EvaluationClotureDTO} or a {@link Page} of {@link EvaluationClotureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EvaluationClotureQueryService extends QueryService<EvaluationCloture> {

    private final Logger log = LoggerFactory.getLogger(EvaluationClotureQueryService.class);

    private final EvaluationClotureRepository evaluationClotureRepository;

    private final EvaluationClotureMapper evaluationClotureMapper;

    public EvaluationClotureQueryService(
        EvaluationClotureRepository evaluationClotureRepository,
        EvaluationClotureMapper evaluationClotureMapper
    ) {
        this.evaluationClotureRepository = evaluationClotureRepository;
        this.evaluationClotureMapper = evaluationClotureMapper;
    }

    /**
     * Return a {@link List} of {@link EvaluationClotureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EvaluationClotureDTO> findByCriteria(EvaluationClotureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EvaluationCloture> specification = createSpecification(criteria);
        return evaluationClotureMapper.toDto(evaluationClotureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EvaluationClotureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EvaluationClotureDTO> findByCriteria(EvaluationClotureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EvaluationCloture> specification = createSpecification(criteria);
        return evaluationClotureRepository.findAll(specification, page).map(evaluationClotureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EvaluationClotureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EvaluationCloture> specification = createSpecification(criteria);
        return evaluationClotureRepository.count(specification);
    }

    /**
     * Function to convert {@link EvaluationClotureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EvaluationCloture> createSpecification(EvaluationClotureCriteria criteria) {
        Specification<EvaluationCloture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EvaluationCloture_.id));
            }
            if (criteria.getLineaire() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLineaire(), EvaluationCloture_.lineaire));
            }
            if (criteria.getCoeff() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoeff(), EvaluationCloture_.coeff));
            }
            if (criteria.getCategoriteClotureId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriteClotureId(),
                            root -> root.join(EvaluationCloture_.categoriteCloture, JoinType.LEFT).get(CategorieCloture_.id)
                        )
                    );
            }
            if (criteria.getDossierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDossierId(),
                            root -> root.join(EvaluationCloture_.dossier, JoinType.LEFT).get(Dossier_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
