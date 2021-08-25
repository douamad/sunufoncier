package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.Quartier;
import com.pirtol.lab.repository.QuartierRepository;
import com.pirtol.lab.service.criteria.QuartierCriteria;
import com.pirtol.lab.service.dto.QuartierDTO;
import com.pirtol.lab.service.mapper.QuartierMapper;
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
 * Service for executing complex queries for {@link Quartier} entities in the database.
 * The main input is a {@link QuartierCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuartierDTO} or a {@link Page} of {@link QuartierDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuartierQueryService extends QueryService<Quartier> {

    private final Logger log = LoggerFactory.getLogger(QuartierQueryService.class);

    private final QuartierRepository quartierRepository;

    private final QuartierMapper quartierMapper;

    public QuartierQueryService(QuartierRepository quartierRepository, QuartierMapper quartierMapper) {
        this.quartierRepository = quartierRepository;
        this.quartierMapper = quartierMapper;
    }

    /**
     * Return a {@link List} of {@link QuartierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuartierDTO> findByCriteria(QuartierCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Quartier> specification = createSpecification(criteria);
        return quartierMapper.toDto(quartierRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuartierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuartierDTO> findByCriteria(QuartierCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Quartier> specification = createSpecification(criteria);
        return quartierRepository.findAll(specification, page).map(quartierMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuartierCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Quartier> specification = createSpecification(criteria);
        return quartierRepository.count(specification);
    }

    /**
     * Function to convert {@link QuartierCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Quartier> createSpecification(QuartierCriteria criteria) {
        Specification<Quartier> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Quartier_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Quartier_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Quartier_.libelle));
            }
            if (criteria.getLotissementId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLotissementId(),
                            root -> root.join(Quartier_.lotissements, JoinType.LEFT).get(Lotissement_.id)
                        )
                    );
            }
            if (criteria.getCommununeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCommununeId(),
                            root -> root.join(Quartier_.communune, JoinType.LEFT).get(Commune_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
