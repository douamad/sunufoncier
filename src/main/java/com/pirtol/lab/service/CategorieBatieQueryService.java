package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.CategorieBatie;
import com.pirtol.lab.repository.CategorieBatieRepository;
import com.pirtol.lab.service.criteria.CategorieBatieCriteria;
import com.pirtol.lab.service.dto.CategorieBatieDTO;
import com.pirtol.lab.service.mapper.CategorieBatieMapper;
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
 * Service for executing complex queries for {@link CategorieBatie} entities in the database.
 * The main input is a {@link CategorieBatieCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategorieBatieDTO} or a {@link Page} of {@link CategorieBatieDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategorieBatieQueryService extends QueryService<CategorieBatie> {

    private final Logger log = LoggerFactory.getLogger(CategorieBatieQueryService.class);

    private final CategorieBatieRepository categorieBatieRepository;

    private final CategorieBatieMapper categorieBatieMapper;

    public CategorieBatieQueryService(CategorieBatieRepository categorieBatieRepository, CategorieBatieMapper categorieBatieMapper) {
        this.categorieBatieRepository = categorieBatieRepository;
        this.categorieBatieMapper = categorieBatieMapper;
    }

    /**
     * Return a {@link List} of {@link CategorieBatieDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategorieBatieDTO> findByCriteria(CategorieBatieCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CategorieBatie> specification = createSpecification(criteria);
        return categorieBatieMapper.toDto(categorieBatieRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategorieBatieDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategorieBatieDTO> findByCriteria(CategorieBatieCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CategorieBatie> specification = createSpecification(criteria);
        return categorieBatieRepository.findAll(specification, page).map(categorieBatieMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategorieBatieCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CategorieBatie> specification = createSpecification(criteria);
        return categorieBatieRepository.count(specification);
    }

    /**
     * Function to convert {@link CategorieBatieCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CategorieBatie> createSpecification(CategorieBatieCriteria criteria) {
        Specification<CategorieBatie> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CategorieBatie_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), CategorieBatie_.libelle));
            }
            if (criteria.getPrixMetreCare() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixMetreCare(), CategorieBatie_.prixMetreCare));
            }
            if (criteria.getEvaluationSurfaceBatieId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEvaluationSurfaceBatieId(),
                            root -> root.join(CategorieBatie_.evaluationSurfaceBaties, JoinType.LEFT).get(EvaluationSurfaceBatie_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
