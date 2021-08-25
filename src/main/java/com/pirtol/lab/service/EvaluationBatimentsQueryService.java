package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.EvaluationBatiments;
import com.pirtol.lab.repository.EvaluationBatimentsRepository;
import com.pirtol.lab.service.criteria.EvaluationBatimentsCriteria;
import com.pirtol.lab.service.dto.EvaluationBatimentsDTO;
import com.pirtol.lab.service.mapper.EvaluationBatimentsMapper;
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
 * Service for executing complex queries for {@link EvaluationBatiments} entities in the database.
 * The main input is a {@link EvaluationBatimentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EvaluationBatimentsDTO} or a {@link Page} of {@link EvaluationBatimentsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EvaluationBatimentsQueryService extends QueryService<EvaluationBatiments> {

    private final Logger log = LoggerFactory.getLogger(EvaluationBatimentsQueryService.class);

    private final EvaluationBatimentsRepository evaluationBatimentsRepository;

    private final EvaluationBatimentsMapper evaluationBatimentsMapper;

    public EvaluationBatimentsQueryService(
        EvaluationBatimentsRepository evaluationBatimentsRepository,
        EvaluationBatimentsMapper evaluationBatimentsMapper
    ) {
        this.evaluationBatimentsRepository = evaluationBatimentsRepository;
        this.evaluationBatimentsMapper = evaluationBatimentsMapper;
    }

    /**
     * Return a {@link List} of {@link EvaluationBatimentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EvaluationBatimentsDTO> findByCriteria(EvaluationBatimentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EvaluationBatiments> specification = createSpecification(criteria);
        return evaluationBatimentsMapper.toDto(evaluationBatimentsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EvaluationBatimentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EvaluationBatimentsDTO> findByCriteria(EvaluationBatimentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EvaluationBatiments> specification = createSpecification(criteria);
        return evaluationBatimentsRepository.findAll(specification, page).map(evaluationBatimentsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EvaluationBatimentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EvaluationBatiments> specification = createSpecification(criteria);
        return evaluationBatimentsRepository.count(specification);
    }

    /**
     * Function to convert {@link EvaluationBatimentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EvaluationBatiments> createSpecification(EvaluationBatimentsCriteria criteria) {
        Specification<EvaluationBatiments> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EvaluationBatiments_.id));
            }
            if (criteria.getNiveau() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNiveau(), EvaluationBatiments_.niveau));
            }
            if (criteria.getSurface() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurface(), EvaluationBatiments_.surface));
            }
            if (criteria.getCoeff() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoeff(), EvaluationBatiments_.coeff));
            }
            if (criteria.getCategorieNatureId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategorieNatureId(),
                            root -> root.join(EvaluationBatiments_.categorieNature, JoinType.LEFT).get(CategorieNature_.id)
                        )
                    );
            }
            if (criteria.getDossierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDossierId(),
                            root -> root.join(EvaluationBatiments_.dossier, JoinType.LEFT).get(Dossier_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
