package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.EvaluationCoursAmenage;
import com.pirtol.lab.repository.EvaluationCoursAmenageRepository;
import com.pirtol.lab.service.criteria.EvaluationCoursAmenageCriteria;
import com.pirtol.lab.service.dto.EvaluationCoursAmenageDTO;
import com.pirtol.lab.service.mapper.EvaluationCoursAmenageMapper;
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
 * Service for executing complex queries for {@link EvaluationCoursAmenage} entities in the database.
 * The main input is a {@link EvaluationCoursAmenageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EvaluationCoursAmenageDTO} or a {@link Page} of {@link EvaluationCoursAmenageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EvaluationCoursAmenageQueryService extends QueryService<EvaluationCoursAmenage> {

    private final Logger log = LoggerFactory.getLogger(EvaluationCoursAmenageQueryService.class);

    private final EvaluationCoursAmenageRepository evaluationCoursAmenageRepository;

    private final EvaluationCoursAmenageMapper evaluationCoursAmenageMapper;

    public EvaluationCoursAmenageQueryService(
        EvaluationCoursAmenageRepository evaluationCoursAmenageRepository,
        EvaluationCoursAmenageMapper evaluationCoursAmenageMapper
    ) {
        this.evaluationCoursAmenageRepository = evaluationCoursAmenageRepository;
        this.evaluationCoursAmenageMapper = evaluationCoursAmenageMapper;
    }

    /**
     * Return a {@link List} of {@link EvaluationCoursAmenageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EvaluationCoursAmenageDTO> findByCriteria(EvaluationCoursAmenageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EvaluationCoursAmenage> specification = createSpecification(criteria);
        return evaluationCoursAmenageMapper.toDto(evaluationCoursAmenageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EvaluationCoursAmenageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EvaluationCoursAmenageDTO> findByCriteria(EvaluationCoursAmenageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EvaluationCoursAmenage> specification = createSpecification(criteria);
        return evaluationCoursAmenageRepository.findAll(specification, page).map(evaluationCoursAmenageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EvaluationCoursAmenageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EvaluationCoursAmenage> specification = createSpecification(criteria);
        return evaluationCoursAmenageRepository.count(specification);
    }

    /**
     * Function to convert {@link EvaluationCoursAmenageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EvaluationCoursAmenage> createSpecification(EvaluationCoursAmenageCriteria criteria) {
        Specification<EvaluationCoursAmenage> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EvaluationCoursAmenage_.id));
            }
            if (criteria.getSurface() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurface(), EvaluationCoursAmenage_.surface));
            }
            if (criteria.getCoeff() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoeff(), EvaluationCoursAmenage_.coeff));
            }
            if (criteria.getCategorieCoursAmenageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategorieCoursAmenageId(),
                            root -> root.join(EvaluationCoursAmenage_.categorieCoursAmenage, JoinType.LEFT).get(CategorieCoursAmenage_.id)
                        )
                    );
            }
            if (criteria.getDossierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDossierId(),
                            root -> root.join(EvaluationCoursAmenage_.dossier, JoinType.LEFT).get(Dossier_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
