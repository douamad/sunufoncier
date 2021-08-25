package com.pirtol.lab.service;

import com.pirtol.lab.domain.*; // for static metamodels
import com.pirtol.lab.domain.Dossier;
import com.pirtol.lab.repository.DossierRepository;
import com.pirtol.lab.service.criteria.DossierCriteria;
import com.pirtol.lab.service.dto.DossierDTO;
import com.pirtol.lab.service.mapper.DossierMapper;
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
 * Service for executing complex queries for {@link Dossier} entities in the database.
 * The main input is a {@link DossierCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DossierDTO} or a {@link Page} of {@link DossierDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DossierQueryService extends QueryService<Dossier> {

    private final Logger log = LoggerFactory.getLogger(DossierQueryService.class);

    private final DossierRepository dossierRepository;

    private final DossierMapper dossierMapper;

    public DossierQueryService(DossierRepository dossierRepository, DossierMapper dossierMapper) {
        this.dossierRepository = dossierRepository;
        this.dossierMapper = dossierMapper;
    }

    /**
     * Return a {@link List} of {@link DossierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DossierDTO> findByCriteria(DossierCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Dossier> specification = createSpecification(criteria);
        return dossierMapper.toDto(dossierRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DossierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DossierDTO> findByCriteria(DossierCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Dossier> specification = createSpecification(criteria);
        return dossierRepository.findAll(specification, page).map(dossierMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DossierCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Dossier> specification = createSpecification(criteria);
        return dossierRepository.count(specification);
    }

    /**
     * Function to convert {@link DossierCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Dossier> createSpecification(DossierCriteria criteria) {
        Specification<Dossier> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Dossier_.id));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), Dossier_.numero));
            }
            if (criteria.getValeurBatie() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValeurBatie(), Dossier_.valeurBatie));
            }
            if (criteria.getValeurVenale() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValeurVenale(), Dossier_.valeurVenale));
            }
            if (criteria.getValeurLocativ() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValeurLocativ(), Dossier_.valeurLocativ));
            }
            if (criteria.getActivite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActivite(), Dossier_.activite));
            }
            if (criteria.getEvaluationSurfaceBatieId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEvaluationSurfaceBatieId(),
                            root -> root.join(Dossier_.evaluationSurfaceBaties, JoinType.LEFT).get(EvaluationSurfaceBatie_.id)
                        )
                    );
            }
            if (criteria.getEvaluationBatimentsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEvaluationBatimentsId(),
                            root -> root.join(Dossier_.evaluationBatiments, JoinType.LEFT).get(EvaluationBatiments_.id)
                        )
                    );
            }
            if (criteria.getEvaluationClotureId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEvaluationClotureId(),
                            root -> root.join(Dossier_.evaluationClotures, JoinType.LEFT).get(EvaluationCloture_.id)
                        )
                    );
            }
            if (criteria.getEvaluationCoursAmenageId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEvaluationCoursAmenageId(),
                            root -> root.join(Dossier_.evaluationCoursAmenages, JoinType.LEFT).get(EvaluationCoursAmenage_.id)
                        )
                    );
            }
            if (criteria.getLocataireId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLocataireId(),
                            root -> root.join(Dossier_.locataires, JoinType.LEFT).get(Locataire_.id)
                        )
                    );
            }
            if (criteria.getDossierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDossierId(), root -> root.join(Dossier_.dossier, JoinType.LEFT).get(Lotissement_.id))
                    );
            }
            if (criteria.getUsageDossierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsageDossierId(),
                            root -> root.join(Dossier_.usageDossier, JoinType.LEFT).get(UsageDossier_.id)
                        )
                    );
            }
            if (criteria.getProprietaireId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProprietaireId(),
                            root -> root.join(Dossier_.proprietaire, JoinType.LEFT).get(Proprietaire_.id)
                        )
                    );
            }
            if (criteria.getRefParcelaireId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRefParcelaireId(),
                            root -> root.join(Dossier_.refParcelaire, JoinType.LEFT).get(RefParcelaire_.id)
                        )
                    );
            }
            if (criteria.getRefcadastraleId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRefcadastraleId(),
                            root -> root.join(Dossier_.refcadastrale, JoinType.LEFT).get(Refcadastrale_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
