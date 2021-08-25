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
 * Criteria class for the {@link com.pirtol.lab.domain.EvaluationSurfaceBatie} entity. This class is used
 * in {@link com.pirtol.lab.web.rest.EvaluationSurfaceBatieResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /evaluation-surface-baties?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EvaluationSurfaceBatieCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter superficieTotale;

    private DoubleFilter superficieBatie;

    private LongFilter categorieBatieId;

    private LongFilter dossierId;

    public EvaluationSurfaceBatieCriteria() {}

    public EvaluationSurfaceBatieCriteria(EvaluationSurfaceBatieCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.superficieTotale = other.superficieTotale == null ? null : other.superficieTotale.copy();
        this.superficieBatie = other.superficieBatie == null ? null : other.superficieBatie.copy();
        this.categorieBatieId = other.categorieBatieId == null ? null : other.categorieBatieId.copy();
        this.dossierId = other.dossierId == null ? null : other.dossierId.copy();
    }

    @Override
    public EvaluationSurfaceBatieCriteria copy() {
        return new EvaluationSurfaceBatieCriteria(this);
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

    public DoubleFilter getSuperficieTotale() {
        return superficieTotale;
    }

    public DoubleFilter superficieTotale() {
        if (superficieTotale == null) {
            superficieTotale = new DoubleFilter();
        }
        return superficieTotale;
    }

    public void setSuperficieTotale(DoubleFilter superficieTotale) {
        this.superficieTotale = superficieTotale;
    }

    public DoubleFilter getSuperficieBatie() {
        return superficieBatie;
    }

    public DoubleFilter superficieBatie() {
        if (superficieBatie == null) {
            superficieBatie = new DoubleFilter();
        }
        return superficieBatie;
    }

    public void setSuperficieBatie(DoubleFilter superficieBatie) {
        this.superficieBatie = superficieBatie;
    }

    public LongFilter getCategorieBatieId() {
        return categorieBatieId;
    }

    public LongFilter categorieBatieId() {
        if (categorieBatieId == null) {
            categorieBatieId = new LongFilter();
        }
        return categorieBatieId;
    }

    public void setCategorieBatieId(LongFilter categorieBatieId) {
        this.categorieBatieId = categorieBatieId;
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
        final EvaluationSurfaceBatieCriteria that = (EvaluationSurfaceBatieCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(superficieTotale, that.superficieTotale) &&
            Objects.equals(superficieBatie, that.superficieBatie) &&
            Objects.equals(categorieBatieId, that.categorieBatieId) &&
            Objects.equals(dossierId, that.dossierId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, superficieTotale, superficieBatie, categorieBatieId, dossierId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationSurfaceBatieCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (superficieTotale != null ? "superficieTotale=" + superficieTotale + ", " : "") +
            (superficieBatie != null ? "superficieBatie=" + superficieBatie + ", " : "") +
            (categorieBatieId != null ? "categorieBatieId=" + categorieBatieId + ", " : "") +
            (dossierId != null ? "dossierId=" + dossierId + ", " : "") +
            "}";
    }
}
