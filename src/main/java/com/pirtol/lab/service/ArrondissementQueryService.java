package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.Arrondissement;
import com.pirtol.lab.repository.ArrondissementRepository;
import com.pirtol.lab.service.criteria.ArrondissementCriteria;
import com.pirtol.lab.service.dto.ArrondissementDTO;
import com.pirtol.lab.service.mapper.ArrondissementMapper;
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
 * Service for executing complex queries for {@link Arrondissement} entities in the database.
 * The main input is a {@link ArrondissementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ArrondissementDTO} or a {@link Page} of {@link ArrondissementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ArrondissementQueryService extends QueryService<Arrondissement> {

    private final Logger log = LoggerFactory.getLogger(ArrondissementQueryService.class);

    private final ArrondissementRepository arrondissementRepository;

    private final ArrondissementMapper arrondissementMapper;

    public ArrondissementQueryService(ArrondissementRepository arrondissementRepository, ArrondissementMapper arrondissementMapper) {
        this.arrondissementRepository = arrondissementRepository;
        this.arrondissementMapper = arrondissementMapper;
    }

    /**
     * Return a {@link List} of {@link ArrondissementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ArrondissementDTO> findByCriteria(ArrondissementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Arrondissement> specification = createSpecification(criteria);
        return arrondissementMapper.toDto(arrondissementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ArrondissementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ArrondissementDTO> findByCriteria(ArrondissementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Arrondissement> specification = createSpecification(criteria);
        return arrondissementRepository.findAll(specification, page).map(arrondissementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ArrondissementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Arrondissement> specification = createSpecification(criteria);
        return arrondissementRepository.count(specification);
    }

    /**
     * Function to convert {@link ArrondissementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Arrondissement> createSpecification(ArrondissementCriteria criteria) {
        Specification<Arrondissement> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Arrondissement_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Arrondissement_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Arrondissement_.libelle));
            }
            if (criteria.getCommuneId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCommuneId(),
                            root -> root.join(Arrondissement_.communes, JoinType.LEFT).get(Commune_.id)
                        )
                    );
            }
            if (criteria.getDepartementId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDepartementId(),
                            root -> root.join(Arrondissement_.departement, JoinType.LEFT).get(Departement_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
