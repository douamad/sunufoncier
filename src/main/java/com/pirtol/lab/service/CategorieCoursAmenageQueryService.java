package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.CategorieCoursAmenage;
import com.pirtol.lab.repository.CategorieCoursAmenageRepository;
import com.pirtol.lab.service.criteria.CategorieCoursAmenageCriteria;
import com.pirtol.lab.service.dto.CategorieCoursAmenageDTO;
import com.pirtol.lab.service.mapper.CategorieCoursAmenageMapper;
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
 * Service for executing complex queries for {@link CategorieCoursAmenage} entities in the database.
 * The main input is a {@link CategorieCoursAmenageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategorieCoursAmenageDTO} or a {@link Page} of {@link CategorieCoursAmenageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategorieCoursAmenageQueryService extends QueryService<CategorieCoursAmenage> {

    private final Logger log = LoggerFactory.getLogger(CategorieCoursAmenageQueryService.class);

    private final CategorieCoursAmenageRepository categorieCoursAmenageRepository;

    private final CategorieCoursAmenageMapper categorieCoursAmenageMapper;

    public CategorieCoursAmenageQueryService(
        CategorieCoursAmenageRepository categorieCoursAmenageRepository,
        CategorieCoursAmenageMapper categorieCoursAmenageMapper
    ) {
        this.categorieCoursAmenageRepository = categorieCoursAmenageRepository;
        this.categorieCoursAmenageMapper = categorieCoursAmenageMapper;
    }

    /**
     * Return a {@link List} of {@link CategorieCoursAmenageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategorieCoursAmenageDTO> findByCriteria(CategorieCoursAmenageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategorieCoursAmenage> specification = createSpecification(criteria);
        return categorieCoursAmenageMapper.toDto(categorieCoursAmenageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategorieCoursAmenageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategorieCoursAmenageDTO> findByCriteria(CategorieCoursAmenageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategorieCoursAmenage> specification = createSpecification(criteria);
        return categorieCoursAmenageRepository.findAll(specification, page).map(categorieCoursAmenageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategorieCoursAmenageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategorieCoursAmenage> specification = createSpecification(criteria);
        return categorieCoursAmenageRepository.count(specification);
    }

    /**
     * Function to convert {@link CategorieCoursAmenageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CategorieCoursAmenage> createSpecification(CategorieCoursAmenageCriteria criteria) {
        Specification<CategorieCoursAmenage> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CategorieCoursAmenage_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), CategorieCoursAmenage_.libelle));
            }
            if (criteria.getPrixMetreCare() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPrixMetreCare(), CategorieCoursAmenage_.prixMetreCare));
            }
            if (criteria.getEvaluationCoursAmenageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEvaluationCoursAmenageId(),
                            root -> root.join(CategorieCoursAmenage_.evaluationCoursAmenages, JoinType.LEFT).get(EvaluationCoursAmenage_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
