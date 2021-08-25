package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.EvaluationSurfaceBatie;
import com.pirtol.lab.repository.EvaluationSurfaceBatieRepository;
import com.pirtol.lab.service.criteria.EvaluationSurfaceBatieCriteria;
import com.pirtol.lab.service.dto.EvaluationSurfaceBatieDTO;
import com.pirtol.lab.service.mapper.EvaluationSurfaceBatieMapper;
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
 * Service for executing complex queries for {@link EvaluationSurfaceBatie} entities in the database.
 * The main input is a {@link EvaluationSurfaceBatieCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EvaluationSurfaceBatieDTO} or a {@link Page} of {@link EvaluationSurfaceBatieDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EvaluationSurfaceBatieQueryService extends QueryService<EvaluationSurfaceBatie> {

    private final Logger log = LoggerFactory.getLogger(EvaluationSurfaceBatieQueryService.class);

    private final EvaluationSurfaceBatieRepository evaluationSurfaceBatieRepository;

    private final EvaluationSurfaceBatieMapper evaluationSurfaceBatieMapper;

    public EvaluationSurfaceBatieQueryService(
        EvaluationSurfaceBatieRepository evaluationSurfaceBatieRepository,
        EvaluationSurfaceBatieMapper evaluationSurfaceBatieMapper
    ) {
        this.evaluationSurfaceBatieRepository = evaluationSurfaceBatieRepository;
        this.evaluationSurfaceBatieMapper = evaluationSurfaceBatieMapper;
    }

    /**
     * Return a {@link List} of {@link EvaluationSurfaceBatieDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EvaluationSurfaceBatieDTO> findByCriteria(EvaluationSurfaceBatieCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EvaluationSurfaceBatie> specification = createSpecification(criteria);
        return evaluationSurfaceBatieMapper.toDto(evaluationSurfaceBatieRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EvaluationSurfaceBatieDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EvaluationSurfaceBatieDTO> findByCriteria(EvaluationSurfaceBatieCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EvaluationSurfaceBatie> specification = createSpecification(criteria);
        return evaluationSurfaceBatieRepository.findAll(specification, page).map(evaluationSurfaceBatieMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EvaluationSurfaceBatieCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EvaluationSurfaceBatie> specification = createSpecification(criteria);
        return evaluationSurfaceBatieRepository.count(specification);
    }

    /**
     * Function to convert {@link EvaluationSurfaceBatieCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EvaluationSurfaceBatie> createSpecification(EvaluationSurfaceBatieCriteria criteria) {
        Specification<EvaluationSurfaceBatie> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EvaluationSurfaceBatie_.id));
            }
            if (criteria.getSuperficieTotale() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSuperficieTotale(), EvaluationSurfaceBatie_.superficieTotale));
            }
            if (criteria.getSuperficieBatie() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSuperficieBatie(), EvaluationSurfaceBatie_.superficieBatie));
            }
            if (criteria.getCategorieBatieId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategorieBatieId(),
                            root -> root.join(EvaluationSurfaceBatie_.categorieBatie, JoinType.LEFT).get(CategorieBatie_.id)
                        )
                    );
            }
            if (criteria.getDossierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDossierId(),
                            root -> root.join(EvaluationSurfaceBatie_.dossier, JoinType.LEFT).get(Dossier_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
