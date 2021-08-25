package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.UsageDossier;
import com.pirtol.lab.repository.UsageDossierRepository;
import com.pirtol.lab.service.criteria.UsageDossierCriteria;
import com.pirtol.lab.service.dto.UsageDossierDTO;
import com.pirtol.lab.service.mapper.UsageDossierMapper;
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
 * Service for executing complex queries for {@link UsageDossier} entities in the database.
 * The main input is a {@link UsageDossierCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UsageDossierDTO} or a {@link Page} of {@link UsageDossierDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsageDossierQueryService extends QueryService<UsageDossier> {

    private final Logger log = LoggerFactory.getLogger(UsageDossierQueryService.class);

    private final UsageDossierRepository usageDossierRepository;

    private final UsageDossierMapper usageDossierMapper;

    public UsageDossierQueryService(UsageDossierRepository usageDossierRepository, UsageDossierMapper usageDossierMapper) {
        this.usageDossierRepository = usageDossierRepository;
        this.usageDossierMapper = usageDossierMapper;
    }

    /**
     * Return a {@link List} of {@link UsageDossierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UsageDossierDTO> findByCriteria(UsageDossierCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UsageDossier> specification = createSpecification(criteria);
        return usageDossierMapper.toDto(usageDossierRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UsageDossierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UsageDossierDTO> findByCriteria(UsageDossierCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UsageDossier> specification = createSpecification(criteria);
        return usageDossierRepository.findAll(specification, page).map(usageDossierMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UsageDossierCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UsageDossier> specification = createSpecification(criteria);
        return usageDossierRepository.count(specification);
    }

    /**
     * Function to convert {@link UsageDossierCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UsageDossier> createSpecification(UsageDossierCriteria criteria) {
        Specification<UsageDossier> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UsageDossier_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), UsageDossier_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), UsageDossier_.libelle));
            }
            if (criteria.getDossierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDossierId(),
                            root -> root.join(UsageDossier_.dossiers, JoinType.LEFT).get(Dossier_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
