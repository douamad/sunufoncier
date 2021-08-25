package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.RefParcelaire;
import com.pirtol.lab.repository.RefParcelaireRepository;
import com.pirtol.lab.service.criteria.RefParcelaireCriteria;
import com.pirtol.lab.service.dto.RefParcelaireDTO;
import com.pirtol.lab.service.mapper.RefParcelaireMapper;
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
 * Service for executing complex queries for {@link RefParcelaire} entities in the database.
 * The main input is a {@link RefParcelaireCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RefParcelaireDTO} or a {@link Page} of {@link RefParcelaireDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RefParcelaireQueryService extends QueryService<RefParcelaire> {

    private final Logger log = LoggerFactory.getLogger(RefParcelaireQueryService.class);

    private final RefParcelaireRepository refParcelaireRepository;

    private final RefParcelaireMapper refParcelaireMapper;

    public RefParcelaireQueryService(RefParcelaireRepository refParcelaireRepository, RefParcelaireMapper refParcelaireMapper) {
        this.refParcelaireRepository = refParcelaireRepository;
        this.refParcelaireMapper = refParcelaireMapper;
    }

    /**
     * Return a {@link List} of {@link RefParcelaireDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RefParcelaireDTO> findByCriteria(RefParcelaireCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RefParcelaire> specification = createSpecification(criteria);
        return refParcelaireMapper.toDto(refParcelaireRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RefParcelaireDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RefParcelaireDTO> findByCriteria(RefParcelaireCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RefParcelaire> specification = createSpecification(criteria);
        return refParcelaireRepository.findAll(specification, page).map(refParcelaireMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RefParcelaireCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RefParcelaire> specification = createSpecification(criteria);
        return refParcelaireRepository.count(specification);
    }

    /**
     * Function to convert {@link RefParcelaireCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RefParcelaire> createSpecification(RefParcelaireCriteria criteria) {
        Specification<RefParcelaire> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RefParcelaire_.id));
            }
            if (criteria.getNumeroParcelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroParcelle(), RefParcelaire_.numeroParcelle));
            }
            if (criteria.getNatureParcelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNatureParcelle(), RefParcelaire_.natureParcelle));
            }
            if (criteria.getBatie() != null) {
                specification = specification.and(buildSpecification(criteria.getBatie(), RefParcelaire_.batie));
            }
            if (criteria.getDossierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDossierId(),
                            root -> root.join(RefParcelaire_.dossiers, JoinType.LEFT).get(Dossier_.id)
                        )
                    );
            }
            if (criteria.getCommuneId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCommuneId(),
                            root -> root.join(RefParcelaire_.commune, JoinType.LEFT).get(Commune_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
