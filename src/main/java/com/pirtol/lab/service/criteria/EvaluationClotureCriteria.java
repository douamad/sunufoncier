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
 * Criteria class for the {@link com.pirtol.lab.domain.EvaluationCloture} entity. This class is used
 * in {@link com.pirtol.lab.web.rest.EvaluationClotureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /evaluation-clotures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EvaluationClotureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter lineaire;

    private DoubleFilter coeff;

    private LongFilter categoriteClotureId;

    private LongFilter dossierId;

    public EvaluationClotureCriteria() {}

    public EvaluationClotureCriteria(EvaluationClotureCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.lineaire = other.lineaire == null ? null : other.lineaire.copy();
        this.coeff = other.coeff == null ? null : other.coeff.copy();
        this.categoriteClotureId = other.categoriteClotureId == null ? null : other.categoriteClotureId.copy();
        this.dossierId = other.dossierId == null ? null : other.dossierId.copy();
    }

    @Override
    public EvaluationClotureCriteria copy() {
        return new EvaluationClotureCriteria(this);
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

    public DoubleFilter getLineaire() {
        return lineaire;
    }

    public DoubleFilter lineaire() {
        if (lineaire == null) {
            lineaire = new DoubleFilter();
        }
        return lineaire;
    }

    public void setLineaire(DoubleFilter lineaire) {
        this.lineaire = lineaire;
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

    public LongFilter getCategoriteClotureId() {
        return categoriteClotureId;
    }

    public LongFilter categoriteClotureId() {
        if (categoriteClotureId == null) {
            categoriteClotureId = new LongFilter();
        }
        return categoriteClotureId;
    }

    public void setCategoriteClotureId(LongFilter categoriteClotureId) {
        this.categoriteClotureId = categoriteClotureId;
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
        final EvaluationClotureCriteria that = (EvaluationClotureCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(lineaire, that.lineaire) &&
            Objects.equals(coeff, that.coeff) &&
            Objects.equals(categoriteClotureId, that.categoriteClotureId) &&
            Objects.equals(dossierId, that.dossierId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lineaire, coeff, categoriteClotureId, dossierId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationClotureCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (lineaire != null ? "lineaire=" + lineaire + ", " : "") +
            (coeff != null ? "coeff=" + coeff + ", " : "") +
            (categoriteClotureId != null ? "categoriteClotureId=" + categoriteClotureId + ", " : "") +
            (dossierId != null ? "dossierId=" + dossierId + ", " : "") +
            "}";
    }
}
