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
 * Criteria class for the {@link com.pirtol.lab.domain.RefParcelaire} entity. This class is used
 * in {@link com.pirtol.lab.web.rest.RefParcelaireResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ref-parcelaires?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RefParcelaireCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter numeroParcelle;

    private StringFilter natureParcelle;

    private BooleanFilter batie;

    private LongFilter dossierId;

    private LongFilter communeId;

    public RefParcelaireCriteria() {}

    public RefParcelaireCriteria(RefParcelaireCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numeroParcelle = other.numeroParcelle == null ? null : other.numeroParcelle.copy();
        this.natureParcelle = other.natureParcelle == null ? null : other.natureParcelle.copy();
        this.batie = other.batie == null ? null : other.batie.copy();
        this.dossierId = other.dossierId == null ? null : other.dossierId.copy();
        this.communeId = other.communeId == null ? null : other.communeId.copy();
    }

    @Override
    public RefParcelaireCriteria copy() {
        return new RefParcelaireCriteria(this);
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

    public StringFilter getNumeroParcelle() {
        return numeroParcelle;
    }

    public StringFilter numeroParcelle() {
        if (numeroParcelle == null) {
            numeroParcelle = new StringFilter();
        }
        return numeroParcelle;
    }

    public void setNumeroParcelle(StringFilter numeroParcelle) {
        this.numeroParcelle = numeroParcelle;
    }

    public StringFilter getNatureParcelle() {
        return natureParcelle;
    }

    public StringFilter natureParcelle() {
        if (natureParcelle == null) {
            natureParcelle = new StringFilter();
        }
        return natureParcelle;
    }

    public void setNatureParcelle(StringFilter natureParcelle) {
        this.natureParcelle = natureParcelle;
    }

    public BooleanFilter getBatie() {
        return batie;
    }

    public BooleanFilter batie() {
        if (batie == null) {
            batie = new BooleanFilter();
        }
        return batie;
    }

    public void setBatie(BooleanFilter batie) {
        this.batie = batie;
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

    public LongFilter getCommuneId() {
        return communeId;
    }

    public LongFilter communeId() {
        if (communeId == null) {
            communeId = new LongFilter();
        }
        return communeId;
    }

    public void setCommuneId(LongFilter communeId) {
        this.communeId = communeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RefParcelaireCriteria that = (RefParcelaireCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(numeroParcelle, that.numeroParcelle) &&
            Objects.equals(natureParcelle, that.natureParcelle) &&
            Objects.equals(batie, that.batie) &&
            Objects.equals(dossierId, that.dossierId) &&
            Objects.equals(communeId, that.communeId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numeroParcelle, natureParcelle, batie, dossierId, communeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RefParcelaireCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (numeroParcelle != null ? "numeroParcelle=" + numeroParcelle + ", " : "") +
            (natureParcelle != null ? "natureParcelle=" + natureParcelle + ", " : "") +
            (batie != null ? "batie=" + batie + ", " : "") +
            (dossierId != null ? "dossierId=" + dossierId + ", " : "") +
            (communeId != null ? "communeId=" + communeId + ", " : "") +
            "}";
    }
}
