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
 * Criteria class for the {@link com.pirtol.lab.domain.EvaluationBatiments} entity. This class is used
 * in {@link com.pirtol.lab.web.rest.EvaluationBatimentsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /evaluation-batiments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EvaluationBatimentsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter niveau;

    private DoubleFilter surface;

    private DoubleFilter coeff;

    private LongFilter categorieNatureId;

    private LongFilter dossierId;

    public EvaluationBatimentsCriteria() {}

    public EvaluationBatimentsCriteria(EvaluationBatimentsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.niveau = other.niveau == null ? null : other.niveau.copy();
        this.surface = other.surface == null ? null : other.surface.copy();
        this.coeff = other.coeff == null ? null : other.coeff.copy();
        this.categorieNatureId = other.categorieNatureId == null ? null : other.categorieNatureId.copy();
        this.dossierId = other.dossierId == null ? null : other.dossierId.copy();
    }

    @Override
    public EvaluationBatimentsCriteria copy() {
        return new EvaluationBatimentsCriteria(this);
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

    public IntegerFilter getNiveau() {
        return niveau;
    }

    public IntegerFilter niveau() {
        if (niveau == null) {
            niveau = new IntegerFilter();
        }
        return niveau;
    }

    public void setNiveau(IntegerFilter niveau) {
        this.niveau = niveau;
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

    public LongFilter getCategorieNatureId() {
        return categorieNatureId;
    }

    public LongFilter categorieNatureId() {
        if (categorieNatureId == null) {
            categorieNatureId = new LongFilter();
        }
        return categorieNatureId;
    }

    public void setCategorieNatureId(LongFilter categorieNatureId) {
        this.categorieNatureId = categorieNatureId;
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
        final EvaluationBatimentsCriteria that = (EvaluationBatimentsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(niveau, that.niveau) &&
            Objects.equals(surface, that.surface) &&
            Objects.equals(coeff, that.coeff) &&
            Objects.equals(categorieNatureId, that.categorieNatureId) &&
            Objects.equals(dossierId, that.dossierId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, niveau, surface, coeff, categorieNatureId, dossierId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationBatimentsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (niveau != null ? "niveau=" + niveau + ", " : "") +
            (surface != null ? "surface=" + surface + ", " : "") +
            (coeff != null ? "coeff=" + coeff + ", " : "") +
            (categorieNatureId != null ? "categorieNatureId=" + categorieNatureId + ", " : "") +
            (dossierId != null ? "dossierId=" + dossierId + ", " : "") +
            "}";
    }
}
