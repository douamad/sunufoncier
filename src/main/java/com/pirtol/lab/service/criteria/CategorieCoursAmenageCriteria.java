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
 * Criteria class for the {@link com.pirtol.lab.domain.CategorieCoursAmenage} entity. This class is used
 * in {@link com.pirtol.lab.web.rest.CategorieCoursAmenageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categorie-cours-amenages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategorieCoursAmenageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelle;

    private DoubleFilter prixMetreCare;

    private LongFilter evaluationCoursAmenageId;

    public CategorieCoursAmenageCriteria() {}

    public CategorieCoursAmenageCriteria(CategorieCoursAmenageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.prixMetreCare = other.prixMetreCare == null ? null : other.prixMetreCare.copy();
        this.evaluationCoursAmenageId = other.evaluationCoursAmenageId == null ? null : other.evaluationCoursAmenageId.copy();
    }

    @Override
    public CategorieCoursAmenageCriteria copy() {
        return new CategorieCoursAmenageCriteria(this);
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

    public LongFilter getEvaluationCoursAmenageId() {
        return evaluationCoursAmenageId;
    }

    public LongFilter evaluationCoursAmenageId() {
        if (evaluationCoursAmenageId == null) {
            evaluationCoursAmenageId = new LongFilter();
        }
        return evaluationCoursAmenageId;
    }

    public void setEvaluationCoursAmenageId(LongFilter evaluationCoursAmenageId) {
        this.evaluationCoursAmenageId = evaluationCoursAmenageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategorieCoursAmenageCriteria that = (CategorieCoursAmenageCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(prixMetreCare, that.prixMetreCare) &&
            Objects.equals(evaluationCoursAmenageId, that.evaluationCoursAmenageId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle, prixMetreCare, evaluationCoursAmenageId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieCoursAmenageCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (libelle != null ? "libelle=" + libelle + ", " : "") +
            (prixMetreCare != null ? "prixMetreCare=" + prixMetreCare + ", " : "") +
            (evaluationCoursAmenageId != null ? "evaluationCoursAmenageId=" + evaluationCoursAmenageId + ", " : "") +
            "}";
    }
}
