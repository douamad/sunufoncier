package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.CategorieNature;
import com.pirtol.lab.repository.CategorieNatureRepository;
import com.pirtol.lab.service.criteria.CategorieNatureCriteria;
import com.pirtol.lab.service.dto.CategorieNatureDTO;
import com.pirtol.lab.service.mapper.CategorieNatureMapper;
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
 * Service for executing complex queries for {@link CategorieNature} entities in the database.
 * The main input is a {@link CategorieNatureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategorieNatureDTO} or a {@link Page} of {@link CategorieNatureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategorieNatureQueryService extends QueryService<CategorieNature> {

    private final Logger log = LoggerFactory.getLogger(CategorieNatureQueryService.class);

    private final CategorieNatureRepository categorieNatureRepository;

    private final CategorieNatureMapper categorieNatureMapper;

    public CategorieNatureQueryService(CategorieNatureRepository categorieNatureRepository, CategorieNatureMapper categorieNatureMapper) {
        this.categorieNatureRepository = categorieNatureRepository;
        this.categorieNatureMapper = categorieNatureMapper;
    }

    /**
     * Return a {@link List} of {@link CategorieNatureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategorieNatureDTO> findByCriteria(CategorieNatureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategorieNature> specification = createSpecification(criteria);
        return categorieNatureMapper.toDto(categorieNatureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategorieNatureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategorieNatureDTO> findByCriteria(CategorieNatureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategorieNature> specification = createSpecification(criteria);
        return categorieNatureRepository.findAll(specification, page).map(categorieNatureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategorieNatureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategorieNature> specification = createSpecification(criteria);
        return categorieNatureRepository.count(specification);
    }

    /**
     * Function to convert {@link CategorieNatureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CategorieNature> createSpecification(CategorieNatureCriteria criteria) {
        Specification<CategorieNature> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CategorieNature_.id));
            }
            if (criteria.getNature() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNature(), CategorieNature_.nature));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), CategorieNature_.libelle));
            }
            if (criteria.getPrixMetreCare() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixMetreCare(), CategorieNature_.prixMetreCare));
            }
            if (criteria.getEvaluationBatimentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEvaluationBatimentsId(),
                            root -> root.join(CategorieNature_.evaluationBatiments, JoinType.LEFT).get(EvaluationBatiments_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
