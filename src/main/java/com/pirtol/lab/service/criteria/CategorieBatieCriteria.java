package com.pirtol.lab.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.pirtol.lab.domain.CategorieBatie} entity. This class is used
 * in {@link com.pirtol.lab.web.rest.CategorieBatieResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categorie-baties?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategorieBatieCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelle;

    private DoubleFilter prixMetreCare;

    private LongFilter evaluationSurfaceBatieId;

    public CategorieBatieCriteria() {}

    public CategorieBatieCriteria(CategorieBatieCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.prixMetreCare = other.prixMetreCare == null ? null : other.prixMetreCare.copy();
        this.evaluationSurfaceBatieId = other.evaluationSurfaceBatieId == null ? null : other.evaluationSurfaceBatieId.copy();
    }

    @Override
    public CategorieBatieCriteria copy() {
        return new CategorieBatieCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public StringFilter libelle() {
        if (libelle == null) {
            libelle = new StringFilter();
        }
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public DoubleFilter getPrixMetreCare() {
        return prixMetreCare;
    }

    public DoubleFilter prixMetreCare() {
        if (prixMetreCare == null) {
            prixMetreCare = new DoubleFilter();
        }
        return prixMetreCare;
    }

    public void setPrixMetreCare(DoubleFilter prixMetreCare) {
        this.prixMetreCare = prixMetreCare;
    }

    public LongFilter getEvaluationSurfaceBatieId() {
        return evaluationSurfaceBatieId;
    }

    public LongFilter evaluationSurfaceBatieId() {
        if (evaluationSurfaceBatieId == null) {
            evaluationSurfaceBatieId = new LongFilter();
        }
        return evaluationSurfaceBatieId;
    }

    public void setEvaluationSurfaceBatieId(LongFilter evaluationSurfaceBatieId) {
        this.evaluationSurfaceBatieId = evaluationSurfaceBatieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategorieBatieCriteria that = (CategorieBatieCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(prixMetreCare, that.prixMetreCare) &&
            Objects.equals(evaluationSurfaceBatieId, that.evaluationSurfaceBatieId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle, prixMetreCare, evaluationSurfaceBatieId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieBatieCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (libelle != null ? "libelle=" + libelle + ", " : "") +
            (prixMetreCare != null ? "prixMetreCare=" + prixMetreCare + ", " : "") +
            (evaluationSurfaceBatieId != null ? "evaluationSurfaceBatieId=" + evaluationSurfaceBatieId + ", " : "") +
            "}";
    }
}
