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
 * Criteria class for the {@link com.pirtol.lab.domain.CategorieNature} entity. This class is used
 * in {@link com.pirtol.lab.web.rest.CategorieNatureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categorie-natures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategorieNatureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nature;

    private StringFilter libelle;

    private DoubleFilter prixMetreCare;

    private LongFilter evaluationBatimentsId;

    public CategorieNatureCriteria() {}

    public CategorieNatureCriteria(CategorieNatureCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nature = other.nature == null ? null : other.nature.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.prixMetreCare = other.prixMetreCare == null ? null : other.prixMetreCare.copy();
        this.evaluationBatimentsId = other.evaluationBatimentsId == null ? null : other.evaluationBatimentsId.copy();
    }

    @Override
    public CategorieNatureCriteria copy() {
        return new CategorieNatureCriteria(this);
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

    public StringFilter getNature() {
        return nature;
    }

    public StringFilter nature() {
        if (nature == null) {
            nature = new StringFilter();
        }
        return nature;
    }

    public void setNature(StringFilter nature) {
        this.nature = nature;
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

    public LongFilter getEvaluationBatimentsId() {
        return evaluationBatimentsId;
    }

    public LongFilter evaluationBatimentsId() {
        if (evaluationBatimentsId == null) {
            evaluationBatimentsId = new LongFilter();
        }
        return evaluationBatimentsId;
    }

    public void setEvaluationBatimentsId(LongFilter evaluationBatimentsId) {
        this.evaluationBatimentsId = evaluationBatimentsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CategorieNatureCriteria that = (CategorieNatureCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nature, that.nature) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(prixMetreCare, that.prixMetreCare) &&
            Objects.equals(evaluationBatimentsId, that.evaluationBatimentsId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nature, libelle, prixMetreCare, evaluationBatimentsId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieNatureCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nature != null ? "nature=" + nature + ", " : "") +
            (libelle != null ? "libelle=" + libelle + ", " : "") +
            (prixMetreCare != null ? "prixMetreCare=" + prixMetreCare + ", " : "") +
            (evaluationBatimentsId != null ? "evaluationBatimentsId=" + evaluationBatimentsId + ", " : "") +
            "}";
    }
}
