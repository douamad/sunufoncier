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
 * Criteria class for the {@link com.pirtol.lab.domain.EvaluationCoursAmenage} entity. This class is used
 * in {@link com.pirtol.lab.web.rest.EvaluationCoursAmenageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /evaluation-cours-amenages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EvaluationCoursAmenageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter surface;

    private DoubleFilter coeff;

    private LongFilter categorieCoursAmenageId;

    private LongFilter dossierId;

    public EvaluationCoursAmenageCriteria() {}

    public EvaluationCoursAmenageCriteria(EvaluationCoursAmenageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.surface = other.surface == null ? null : other.surface.copy();
        this.coeff = other.coeff == null ? null : other.coeff.copy();
        this.categorieCoursAmenageId = other.categorieCoursAmenageId == null ? null : other.categorieCoursAmenageId.copy();
        this.dossierId = other.dossierId == null ? null : other.dossierId.copy();
    }

    @Override
    public EvaluationCoursAmenageCriteria copy() {
        return new EvaluationCoursAmenageCriteria(this);
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

    public DoubleFilter getSurface() {
        return surface;
    }

    public DoubleFilter surface() {
        if (surface == null) {
            surface = new DoubleFilter();
        }
        return surface;
    }

    public void setSurface(DoubleFilter surface) {
        this.surface = surface;
    }

    public DoubleFilter getCoeff() {
        return coeff;
    }

    public DoubleFilter coeff() {
        if (coeff == null) {
            coeff = new DoubleFilter();
        }
        return coeff;
    }

    public void setCoeff(DoubleFilter coeff) {
        this.coeff = coeff;
    }

    public LongFilter getCategorieCoursAmenageId() {
        return categorieCoursAmenageId;
    }

    public LongFilter categorieCoursAmenageId() {
        if (categorieCoursAmenageId == null) {
            categorieCoursAmenageId = new LongFilter();
        }
        return categorieCoursAmenageId;
    }

    public void setCategorieCoursAmenageId(LongFilter categorieCoursAmenageId) {
        this.categorieCoursAmenageId = categorieCoursAmenageId;
    }

    public LongFilter getDossierId() {
        return dossierId;
    }

    public LongFilter dossierId() {
        if (dossierId == null) {
            dossierId = new LongFilter();
        }
        return dossierId;
    }

    public void setDossierId(LongFilter dossierId) {
        this.dossierId = dossierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EvaluationCoursAmenageCriteria that = (EvaluationCoursAmenageCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(surface, that.surface) &&
            Objects.equals(coeff, that.coeff) &&
            Objects.equals(categorieCoursAmenageId, that.categorieCoursAmenageId) &&
            Objects.equals(dossierId, that.dossierId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surface, coeff, categorieCoursAmenageId, dossierId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationCoursAmenageCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (surface != null ? "surface=" + surface + ", " : "") +
            (coeff != null ? "coeff=" + coeff + ", " : "") +
            (categorieCoursAmenageId != null ? "categorieCoursAmenageId=" + categorieCoursAmenageId + ", " : "") +
            (dossierId != null ? "dossierId=" + dossierId + ", " : "") +
            "}";
    }
}
