package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.Lotissement;
import com.pirtol.lab.repository.LotissementRepository;
import com.pirtol.lab.service.criteria.LotissementCriteria;
import com.pirtol.lab.service.dto.LotissementDTO;
import com.pirtol.lab.service.mapper.LotissementMapper;
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
 * Service for executing complex queries for {@link Lotissement} entities in the database.
 * The main input is a {@link LotissementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LotissementDTO} or a {@link Page} of {@link LotissementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LotissementQueryService extends QueryService<Lotissement> {

    private final Logger log = LoggerFactory.getLogger(LotissementQueryService.class);

    private final LotissementRepository lotissementRepository;

    private final LotissementMapper lotissementMapper;

    public LotissementQueryService(LotissementRepository lotissementRepository, LotissementMapper lotissementMapper) {
        this.lotissementRepository = lotissementRepository;
        this.lotissementMapper = lotissementMapper;
    }

    /**
     * Return a {@link List} of {@link LotissementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LotissementDTO> findByCriteria(LotissementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Lotissement> specification = createSpecification(criteria);
        return lotissementMapper.toDto(lotissementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LotissementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LotissementDTO> findByCriteria(LotissementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Lotissement> specification = createSpecification(criteria);
        return lotissementRepository.findAll(specification, page).map(lotissementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LotissementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Lotissement> specification = createSpecification(criteria);
        return lotissementRepository.count(specification);
    }

    /**
     * Function to convert {@link LotissementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Lotissement> createSpecification(LotissementCriteria criteria) {
        Specification<Lotissement> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Lotissement_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Lotissement_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Lotissement_.libelle));
            }
            if (criteria.getLotissementId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLotissementId(),
                            root -> root.join(Lotissement_.lotissements, JoinType.LEFT).get(Dossier_.id)
                        )
                    );
            }
            if (criteria.getQuartierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getQuartierId(),
                            root -> root.join(Lotissement_.quartier, JoinType.LEFT).get(Quartier_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
