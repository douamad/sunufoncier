package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.Refcadastrale;
import com.pirtol.lab.repository.RefcadastraleRepository;
import com.pirtol.lab.service.criteria.RefcadastraleCriteria;
import com.pirtol.lab.service.dto.RefcadastraleDTO;
import com.pirtol.lab.service.mapper.RefcadastraleMapper;
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
 * Service for executing complex queries for {@link Refcadastrale} entities in the database.
 * The main input is a {@link RefcadastraleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RefcadastraleDTO} or a {@link Page} of {@link RefcadastraleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RefcadastraleQueryService extends QueryService<Refcadastrale> {

    private final Logger log = LoggerFactory.getLogger(RefcadastraleQueryService.class);

    private final RefcadastraleRepository refcadastraleRepository;

    private final RefcadastraleMapper refcadastraleMapper;

    public RefcadastraleQueryService(RefcadastraleRepository refcadastraleRepository, RefcadastraleMapper refcadastraleMapper) {
        this.refcadastraleRepository = refcadastraleRepository;
        this.refcadastraleMapper = refcadastraleMapper;
    }

    /**
     * Return a {@link List} of {@link RefcadastraleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RefcadastraleDTO> findByCriteria(RefcadastraleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Refcadastrale> specification = createSpecification(criteria);
        return refcadastraleMapper.toDto(refcadastraleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RefcadastraleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RefcadastraleDTO> findByCriteria(RefcadastraleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Refcadastrale> specification = createSpecification(criteria);
        return refcadastraleRepository.findAll(specification, page).map(refcadastraleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RefcadastraleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Refcadastrale> specification = createSpecification(criteria);
        return refcadastraleRepository.count(specification);
    }

    /**
     * Function to convert {@link RefcadastraleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Refcadastrale> createSpecification(RefcadastraleCriteria criteria) {
        Specification<Refcadastrale> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Refcadastrale_.id));
            }
            if (criteria.getCodeSection() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeSection(), Refcadastrale_.codeSection));
            }
            if (criteria.getCodeParcelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodeParcelle(), Refcadastrale_.codeParcelle));
            }
            if (criteria.getNicad() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNicad(), Refcadastrale_.nicad));
            }
            if (criteria.getSuperfici() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSuperfici(), Refcadastrale_.superfici));
            }
            if (criteria.getTitreMere() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitreMere(), Refcadastrale_.titreMere));
            }
            if (criteria.getTitreCree() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitreCree(), Refcadastrale_.titreCree));
            }
            if (criteria.getNumeroRequisition() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getNumeroRequisition(), Refcadastrale_.numeroRequisition));
            }
            if (criteria.getDateBornage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateBornage(), Refcadastrale_.dateBornage));
            }
            if (criteria.getDossierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDossierId(),
                            root -> root.join(Refcadastrale_.dossiers, JoinType.LEFT).get(Dossier_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
