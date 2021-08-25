package com.pirtol.lab.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.pirtol.lab.domain.Refcadastrale} entity. This class is used
 * in {@link com.pirtol.lab.web.rest.RefcadastraleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /refcadastrales?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RefcadastraleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codeSection;

    private StringFilter codeParcelle;

    private StringFilter nicad;

    private DoubleFilter superfici;

    private StringFilter titreMere;

    private StringFilter titreCree;

    private StringFilter numeroRequisition;

    private InstantFilter dateBornage;

    private LongFilter dossierId;

    public RefcadastraleCriteria() {}

    public RefcadastraleCriteria(RefcadastraleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codeSection = other.codeSection == null ? null : other.codeSection.copy();
        this.codeParcelle = other.codeParcelle == null ? null : other.codeParcelle.copy();
        this.nicad = other.nicad == null ? null : other.nicad.copy();
        this.superfici = other.superfici == null ? null : other.superfici.copy();
        this.titreMere = other.titreMere == null ? null : other.titreMere.copy();
        this.titreCree = other.titreCree == null ? null : other.titreCree.copy();
        this.numeroRequisition = other.numeroRequisition == null ? null : other.numeroRequisition.copy();
        this.dateBornage = other.dateBornage == null ? null : other.dateBornage.copy();
        this.dossierId = other.dossierId == null ? null : other.dossierId.copy();
    }

    @Override
    public RefcadastraleCriteria copy() {
        return new RefcadastraleCriteria(this);
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

    public StringFilter getCodeSection() {
        return codeSection;
    }

    public StringFilter codeSection() {
        if (codeSection == null) {
            codeSection = new StringFilter();
        }
        return codeSection;
    }

    public void setCodeSection(StringFilter codeSection) {
        this.codeSection = codeSection;
    }

    public StringFilter getCodeParcelle() {
        return codeParcelle;
    }

    public StringFilter codeParcelle() {
        if (codeParcelle == null) {
            codeParcelle = new StringFilter();
        }
        return codeParcelle;
    }

    public void setCodeParcelle(StringFilter codeParcelle) {
        this.codeParcelle = codeParcelle;
    }

    public StringFilter getNicad() {
        return nicad;
    }

    public StringFilter nicad() {
        if (nicad == null) {
            nicad = new StringFilter();
        }
        return nicad;
    }

    public void setNicad(StringFilter nicad) {
        this.nicad = nicad;
    }

    public DoubleFilter getSuperfici() {
        return superfici;
    }

    public DoubleFilter superfici() {
        if (superfici == null) {
            superfici = new DoubleFilter();
        }
        return superfici;
    }

    public void setSuperfici(DoubleFilter superfici) {
        this.superfici = superfici;
    }

    public StringFilter getTitreMere() {
        return titreMere;
    }

    public StringFilter titreMere() {
        if (titreMere == null) {
            titreMere = new StringFilter();
        }
        return titreMere;
    }

    public void setTitreMere(StringFilter titreMere) {
        this.titreMere = titreMere;
    }

    public StringFilter getTitreCree() {
        return titreCree;
    }

    public StringFilter titreCree() {
        if (titreCree == null) {
            titreCree = new StringFilter();
        }
        return titreCree;
    }

    public void setTitreCree(StringFilter titreCree) {
        this.titreCree = titreCree;
    }

    public StringFilter getNumeroRequisition() {
        return numeroRequisition;
    }

    public StringFilter numeroRequisition() {
        if (numeroRequisition == null) {
            numeroRequisition = new StringFilter();
        }
        return numeroRequisition;
    }

    public void setNumeroRequisition(StringFilter numeroRequisition) {
        this.numeroRequisition = numeroRequisition;
    }

    public InstantFilter getDateBornage() {
        return dateBornage;
    }

    public InstantFilter dateBornage() {
        if (dateBornage == null) {
            dateBornage = new InstantFilter();
        }
        return dateBornage;
    }

    public void setDateBornage(InstantFilter dateBornage) {
        this.dateBornage = dateBornage;
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
        final RefcadastraleCriteria that = (RefcadastraleCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(codeSection, that.codeSection) &&
            Objects.equals(codeParcelle, that.codeParcelle) &&
            Objects.equals(nicad, that.nicad) &&
            Objects.equals(superfici, that.superfici) &&
            Objects.equals(titreMere, that.titreMere) &&
            Objects.equals(titreCree, that.titreCree) &&
            Objects.equals(numeroRequisition, that.numeroRequisition) &&
            Objects.equals(dateBornage, that.dateBornage) &&
            Objects.equals(dossierId, that.dossierId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            codeSection,
            codeParcelle,
            nicad,
            superfici,
            titreMere,
            titreCree,
            numeroRequisition,
            dateBornage,
            dossierId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RefcadastraleCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (codeSection != null ? "codeSection=" + codeSection + ", " : "") +
            (codeParcelle != null ? "codeParcelle=" + codeParcelle + ", " : "") +
            (nicad != null ? "nicad=" + nicad + ", " : "") +
            (superfici != null ? "superfici=" + superfici + ", " : "") +
            (titreMere != null ? "titreMere=" + titreMere + ", " : "") +
            (titreCree != null ? "titreCree=" + titreCree + ", " : "") +
            (numeroRequisition != null ? "numeroRequisition=" + numeroRequisition + ", " : "") +
            (dateBornage != null ? "dateBornage=" + dateBornage + ", " : "") +
            (dossierId != null ? "dossierId=" + dossierId + ", " : "") +
            "}";
    }
}
