package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.Commune;
import com.pirtol.lab.repository.CommuneRepository;
import com.pirtol.lab.service.criteria.CommuneCriteria;
import com.pirtol.lab.service.dto.CommuneDTO;
import com.pirtol.lab.service.mapper.CommuneMapper;
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
 * Service for executing complex queries for {@link Commune} entities in the database.
 * The main input is a {@link CommuneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommuneDTO} or a {@link Page} of {@link CommuneDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommuneQueryService extends QueryService<Commune> {

    private final Logger log = LoggerFactory.getLogger(CommuneQueryService.class);

    private final CommuneRepository communeRepository;

    private final CommuneMapper communeMapper;

    public CommuneQueryService(CommuneRepository communeRepository, CommuneMapper communeMapper) {
        this.communeRepository = communeRepository;
        this.communeMapper = communeMapper;
    }

    /**
     * Return a {@link List} of {@link CommuneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommuneDTO> findByCriteria(CommuneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Commune> specification = createSpecification(criteria);
        return communeMapper.toDto(communeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommuneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommuneDTO> findByCriteria(CommuneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Commune> specification = createSpecification(criteria);
        return communeRepository.findAll(specification, page).map(communeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommuneCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Commune> specification = createSpecification(criteria);
        return communeRepository.count(specification);
    }

    /**
     * Function to convert {@link CommuneCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Commune> createSpecification(CommuneCriteria criteria) {
        Specification<Commune> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Commune_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Commune_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Commune_.libelle));
            }
            if (criteria.getQuartierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getQuartierId(), root -> root.join(Commune_.quartiers, JoinType.LEFT).get(Quartier_.id))
                    );
            }
            if (criteria.getArrondissementId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getArrondissementId(),
                            root -> root.join(Commune_.arrondissement, JoinType.LEFT).get(Arrondissement_.id)
                        )
                    );
            }
            if (criteria.getRefParcelaireId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRefParcelaireId(),
                            root -> root.join(Commune_.refParcelaires, JoinType.LEFT).get(RefParcelaire_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
