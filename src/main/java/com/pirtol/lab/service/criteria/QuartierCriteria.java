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
 * Criteria class for the {@link com.pirtol.lab.domain.Quartier} entity. This class is used
 * in {@link com.pirtol.lab.web.rest.QuartierResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /quartiers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuartierCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter libelle;

    private LongFilter lotissementId;

    private LongFilter commununeId;

    public QuartierCriteria() {}

    public QuartierCriteria(QuartierCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.libelle = other.libelle == null ? null : other.libelle.copy();
        this.lotissementId = other.lotissementId == null ? null : other.lotissementId.copy();
        this.commununeId = other.commununeId == null ? null : other.commununeId.copy();
    }

    @Override
    public QuartierCriteria copy() {
        return new QuartierCriteria(this);
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

    public LongFilter getLotissementId() {
        return lotissementId;
    }

    public LongFilter lotissementId() {
        if (lotissementId == null) {
            lotissementId = new LongFilter();
        }
        return lotissementId;
    }

    public void setLotissementId(LongFilter lotissementId) {
        this.lotissementId = lotissementId;
    }

    public LongFilter getCommununeId() {
        return commununeId;
    }

    public LongFilter commununeId() {
        if (commununeId == null) {
            commununeId = new LongFilter();
        }
        return commununeId;
    }

    public void setCommununeId(LongFilter commununeId) {
        this.commununeId = commununeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QuartierCriteria that = (QuartierCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(lotissementId, that.lotissementId) &&
            Objects.equals(commununeId, that.commununeId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, libelle, lotissementId, commununeId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuartierCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (libelle != null ? "libelle=" + libelle + ", " : "") +
            (lotissementId != null ? "lotissementId=" + lotissementId + ", " : "") +
            (commununeId != null ? "commununeId=" + commununeId + ", " : "") +
            "}";
    }
}
