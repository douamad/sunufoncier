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
 * Criteria class for the {@link com.pirtol.lab.domain.Commune} entity. This class is used
 * in {@link com.pirtol.lab.web.rest.CommuneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /communes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommuneCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter libelle;

    private LongFilter quartierId;

    private LongFilter arrondissementId;

    private LongFilter refParcelaireId;

    public CommuneCriteria() {}

    public CommuneCriteria(CommuneCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.quartierId = other.quartierId == null ? null : other.quartierId.copy();
        this.arrondissementId = other.arrondissementId == null ? null : other.arrondissementId.copy();
        this.refParcelaireId = other.refParcelaireId == null ? null : other.refParcelaireId.copy();
    }

    @Override
    public CommuneCriteria copy() {
        return new CommuneCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
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

    public LongFilter getQuartierId() {
        return quartierId;
    }

    public LongFilter quartierId() {
        if (quartierId == null) {
            quartierId = new LongFilter();
        }
        return quartierId;
    }

    public void setQuartierId(LongFilter quartierId) {
        this.quartierId = quartierId;
    }

    public LongFilter getArrondissementId() {
        return arrondissementId;
    }

    public LongFilter arrondissementId() {
        if (arrondissementId == null) {
            arrondissementId = new LongFilter();
        }
        return arrondissementId;
    }

    public void setArrondissementId(LongFilter arrondissementId) {
        this.arrondissementId = arrondissementId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommuneCriteria that = (CommuneCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(quartierId, that.quartierId) &&
            Objects.equals(arrondissementId, that.arrondissementId) &&
            Objects.equals(refParcelaireId, that.refParcelaireId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, libelle, quartierId, arrondissementId, refParcelaireId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommuneCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (libelle != null ? "libelle=" + libelle + ", " : "") +
            (quartierId != null ? "quartierId=" + quartierId + ", " : "") +
            (arrondissementId != null ? "arrondissementId=" + arrondissementId + ", " : "") +
            (refParcelaireId != null ? "refParcelaireId=" + refParcelaireId + ", " : "") +
            "}";
    }
}
