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
 * Criteria class for the {@link com.pirtol.lab.domain.Dossier} entity. This class is used
 * in {@link com.pirtol.lab.web.rest.DossierResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dossiers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DossierCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter numero;

    private DoubleFilter valeurBatie;

    private DoubleFilter valeurVenale;

    private DoubleFilter valeurLocativ;

    private StringFilter activite;

    private LongFilter evaluationSurfaceBatieId;

    private LongFilter evaluationBatimentsId;

    private LongFilter evaluationClotureId;

    private LongFilter evaluationCoursAmenageId;

    private LongFilter locataireId;

    private LongFilter dossierId;

    private LongFilter usageDossierId;

    private LongFilter proprietaireId;

    private LongFilter refParcelaireId;

    private LongFilter refcadastraleId;

    public DossierCriteria() {}

    public DossierCriteria(DossierCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numero = other.numero == null ? null : other.numero.copy();
        this.valeurBatie = other.valeurBatie == null ? null : other.valeurBatie.copy();
        this.valeurVenale = other.valeurVenale == null ? null : other.valeurVenale.copy();
        this.valeurLocativ = other.valeurLocativ == null ? null : other.valeurLocativ.copy();
        this.activite = other.activite == null ? null : other.activite.copy();
        this.evaluationSurfaceBatieId = other.evaluationSurfaceBatieId == null ? null : other.evaluationSurfaceBatieId.copy();
        this.evaluationBatimentsId = other.evaluationBatimentsId == null ? null : other.evaluationBatimentsId.copy();
        this.evaluationClotureId = other.evaluationClotureId == null ? null : other.evaluationClotureId.copy();
        this.evaluationCoursAmenageId = other.evaluationCoursAmenageId == null ? null : other.evaluationCoursAmenageId.copy();
        this.locataireId = other.locataireId == null ? null : other.locataireId.copy();
        this.dossierId = other.dossierId == null ? null : other.dossierId.copy();
        this.usageDossierId = other.usageDossierId == null ? null : other.usageDossierId.copy();
        this.proprietaireId = other.proprietaireId == null ? null : other.proprietaireId.copy();
        this.refParcelaireId = other.refParcelaireId == null ? null : other.refParcelaireId.copy();
        this.refcadastraleId = other.refcadastraleId == null ? null : other.refcadastraleId.copy();
    }

    @Override
    public DossierCriteria copy() {
        return new DossierCriteria(this);
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

    public StringFilter getNumero() {
        return numero;
    }

    public StringFilter numero() {
        if (numero == null) {
            numero = new StringFilter();
        }
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public DoubleFilter getValeurBatie() {
        return valeurBatie;
    }

    public DoubleFilter valeurBatie() {
        if (valeurBatie == null) {
            valeurBatie = new DoubleFilter();
        }
        return valeurBatie;
    }

    public void setValeurBatie(DoubleFilter valeurBatie) {
        this.valeurBatie = valeurBatie;
    }

    public DoubleFilter getValeurVenale() {
        return valeurVenale;
    }

    public DoubleFilter valeurVenale() {
        if (valeurVenale == null) {
            valeurVenale = new DoubleFilter();
        }
        return valeurVenale;
    }

    public void setValeurVenale(DoubleFilter valeurVenale) {
        this.valeurVenale = valeurVenale;
    }

    public DoubleFilter getValeurLocativ() {
        return valeurLocativ;
    }

    public DoubleFilter valeurLocativ() {
        if (valeurLocativ == null) {
            valeurLocativ = new DoubleFilter();
        }
        return valeurLocativ;
    }

    public void setValeurLocativ(DoubleFilter valeurLocativ) {
        this.valeurLocativ = valeurLocativ;
    }

    public StringFilter getActivite() {
        return activite;
    }

    public StringFilter activite() {
        if (activite == null) {
            activite = new StringFilter();
        }
        return activite;
    }

    public void setActivite(StringFilter activite) {
        this.activite = activite;
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

    public LongFilter getEvaluationClotureId() {
        return evaluationClotureId;
    }

    public LongFilter evaluationClotureId() {
        if (evaluationClotureId == null) {
            evaluationClotureId = new LongFilter();
        }
        return evaluationClotureId;
    }

    public void setEvaluationClotureId(LongFilter evaluationClotureId) {
        this.evaluationClotureId = evaluationClotureId;
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

    public LongFilter getLocataireId() {
        return locataireId;
    }

    public LongFilter locataireId() {
        if (locataireId == null) {
            locataireId = new LongFilter();
        }
        return locataireId;
    }

    public void setLocataireId(LongFilter locataireId) {
        this.locataireId = locataireId;
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

    public LongFilter getUsageDossierId() {
        return usageDossierId;
    }

    public LongFilter usageDossierId() {
        if (usageDossierId == null) {
            usageDossierId = new LongFilter();
        }
        return usageDossierId;
    }

    public void setUsageDossierId(LongFilter usageDossierId) {
        this.usageDossierId = usageDossierId;
    }

    public LongFilter getProprietaireId() {
        return proprietaireId;
    }

    public LongFilter proprietaireId() {
        if (proprietaireId == null) {
            proprietaireId = new LongFilter();
        }
        return proprietaireId;
    }

    public void setProprietaireId(LongFilter proprietaireId) {
        this.proprietaireId = proprietaireId;
    }

    public LongFilter getRefParcelaireId() {
        return refParcelaireId;
    }

    public LongFilter refParcelaireId() {
        if (refParcelaireId == null) {
            refParcelaireId = new LongFilter();
        }
        return refParcelaireId;
    }

    public void setRefParcelaireId(LongFilter refParcelaireId) {
        this.refParcelaireId = refParcelaireId;
    }

    public LongFilter getRefcadastraleId() {
        return refcadastraleId;
    }

    public LongFilter refcadastraleId() {
        if (refcadastraleId == null) {
            refcadastraleId = new LongFilter();
        }
        return refcadastraleId;
    }

    public void setRefcadastraleId(LongFilter refcadastraleId) {
        this.refcadastraleId = refcadastraleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DossierCriteria that = (DossierCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(valeurBatie, that.valeurBatie) &&
            Objects.equals(valeurVenale, that.valeurVenale) &&
            Objects.equals(valeurLocativ, that.valeurLocativ) &&
            Objects.equals(activite, that.activite) &&
            Objects.equals(evaluationSurfaceBatieId, that.evaluationSurfaceBatieId) &&
            Objects.equals(evaluationBatimentsId, that.evaluationBatimentsId) &&
            Objects.equals(evaluationClotureId, that.evaluationClotureId) &&
            Objects.equals(evaluationCoursAmenageId, that.evaluationCoursAmenageId) &&
            Objects.equals(locataireId, that.locataireId) &&
            Objects.equals(dossierId, that.dossierId) &&
            Objects.equals(usageDossierId, that.usageDossierId) &&
            Objects.equals(proprietaireId, that.proprietaireId) &&
            Objects.equals(refParcelaireId, that.refParcelaireId) &&
            Objects.equals(refcadastraleId, that.refcadastraleId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            numero,
            valeurBatie,
            valeurVenale,
            valeurLocativ,
            activite,
            evaluationSurfaceBatieId,
            evaluationBatimentsId,
            evaluationClotureId,
            evaluationCoursAmenageId,
            locataireId,
            dossierId,
            usageDossierId,
            proprietaireId,
            refParcelaireId,
            refcadastraleId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DossierCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (numero != null ? "numero=" + numero + ", " : "") +
            (valeurBatie != null ? "valeurBatie=" + valeurBatie + ", " : "") +
            (valeurVenale != null ? "valeurVenale=" + valeurVenale + ", " : "") +
            (valeurLocativ != null ? "valeurLocativ=" + valeurLocativ + ", " : "") +
            (activite != null ? "activite=" + activite + ", " : "") +
            (evaluationSurfaceBatieId != null ? "evaluationSurfaceBatieId=" + evaluationSurfaceBatieId + ", " : "") +
            (evaluationBatimentsId != null ? "evaluationBatimentsId=" + evaluationBatimentsId + ", " : "") +
            (evaluationClotureId != null ? "evaluationClotureId=" + evaluationClotureId + ", " : "") +
            (evaluationCoursAmenageId != null ? "evaluationCoursAmenageId=" + evaluationCoursAmenageId + ", " : "") +
            (locataireId != null ? "locataireId=" + locataireId + ", " : "") +
            (dossierId != null ? "dossierId=" + dossierId + ", " : "") +
            (usageDossierId != null ? "usageDossierId=" + usageDossierId + ", " : "") +
            (proprietaireId != null ? "proprietaireId=" + proprietaireId + ", " : "") +
            (refParcelaireId != null ? "refParcelaireId=" + refParcelaireId + ", " : "") +
            (refcadastraleId != null ? "refcadastraleId=" + refcadastraleId + ", " : "") +
            "}";
    }
}
