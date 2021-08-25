package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.Nature;
import com.pirtol.lab.repository.NatureRepository;
import com.pirtol.lab.service.criteria.NatureCriteria;
import com.pirtol.lab.service.dto.NatureDTO;
import com.pirtol.lab.service.mapper.NatureMapper;
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
 * Service for executing complex queries for {@link Nature} entities in the database.
 * The main input is a {@link NatureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NatureDTO} or a {@link Page} of {@link NatureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NatureQueryService extends QueryService<Nature> {

    private final Logger log = LoggerFactory.getLogger(NatureQueryService.class);

    private final NatureRepository natureRepository;

    private final NatureMapper natureMapper;

    public NatureQueryService(NatureRepository natureRepository, NatureMapper natureMapper) {
        this.natureRepository = natureRepository;
        this.natureMapper = natureMapper;
    }

    /**
     * Return a {@link List} of {@link NatureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NatureDTO> findByCriteria(NatureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Nature> specification = createSpecification(criteria);
        return natureMapper.toDto(natureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NatureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NatureDTO> findByCriteria(NatureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Nature> specification = createSpecification(criteria);
        return natureRepository.findAll(specification, page).map(natureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NatureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Nature> specification = createSpecification(criteria);
        return natureRepository.count(specification);
    }

    /**
     * Function to convert {@link NatureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Nature> createSpecification(NatureCriteria criteria) {
        Specification<Nature> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Nature_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Nature_.code));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Nature_.libelle));
            }
        }
        return specification;
    }
}
