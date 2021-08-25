package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.CategorieCloture;
import com.pirtol.lab.repository.CategorieClotureRepository;
import com.pirtol.lab.service.criteria.CategorieClotureCriteria;
import com.pirtol.lab.service.dto.CategorieClotureDTO;
import com.pirtol.lab.service.mapper.CategorieClotureMapper;
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
 * Service for executing complex queries for {@link CategorieCloture} entities in the database.
 * The main input is a {@link CategorieClotureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategorieClotureDTO} or a {@link Page} of {@link CategorieClotureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategorieClotureQueryService extends QueryService<CategorieCloture> {

    private final Logger log = LoggerFactory.getLogger(CategorieClotureQueryService.class);

    private final CategorieClotureRepository categorieClotureRepository;

    private final CategorieClotureMapper categorieClotureMapper;

    public CategorieClotureQueryService(
        CategorieClotureRepository categorieClotureRepository,
        CategorieClotureMapper categorieClotureMapper
    ) {
        this.categorieClotureRepository = categorieClotureRepository;
        this.categorieClotureMapper = categorieClotureMapper;
    }

    /**
     * Return a {@link List} of {@link CategorieClotureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategorieClotureDTO> findByCriteria(CategorieClotureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategorieCloture> specification = createSpecification(criteria);
        return categorieClotureMapper.toDto(categorieClotureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategorieClotureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategorieClotureDTO> findByCriteria(CategorieClotureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategorieCloture> specification = createSpecification(criteria);
        return categorieClotureRepository.findAll(specification, page).map(categorieClotureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategorieClotureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategorieCloture> specification = createSpecification(criteria);
        return categorieClotureRepository.count(specification);
    }

    /**
     * Function to convert {@link CategorieClotureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CategorieCloture> createSpecification(CategorieClotureCriteria criteria) {
        Specification<CategorieCloture> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CategorieCloture_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), CategorieCloture_.libelle));
            }
            if (criteria.getPrixMetreCare() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixMetreCare(), CategorieCloture_.prixMetreCare));
            }
            if (criteria.getEvaluationClotureId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEvaluationClotureId(),
                            root -> root.join(CategorieCloture_.evaluationClotures, JoinType.LEFT).get(EvaluationCloture_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
