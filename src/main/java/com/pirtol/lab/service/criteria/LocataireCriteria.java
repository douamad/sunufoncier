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
 * Criteria class for the {@link com.pirtol.lab.domain.Locataire} entity. This class is used
 * in {@link com.pirtol.lab.web.rest.LocataireResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /locataires?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LocataireCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nom;

    private BooleanFilter personne;

    private StringFilter activite;

    private StringFilter ninea;

    private DoubleFilter montant;

    private LongFilter dossierId;

    public LocataireCriteria() {}

    public LocataireCriteria(LocataireCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.personne = other.personne == null ? null : other.personne.copy();
        this.activite = other.activite == null ? null : other.activite.copy();
        this.ninea = other.ninea == null ? null : other.ninea.copy();
        this.montant = other.montant == null ? null : other.montant.copy();
        this.dossierId = other.dossierId == null ? null : other.dossierId.copy();
    }

    @Override
    public LocataireCriteria copy() {
        return new LocataireCriteria(this);
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

    public StringFilter getNom() {
        return nom;
    }

    public StringFilter nom() {
        if (nom == null) {
            nom = new StringFilter();
        }
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public BooleanFilter getPersonne() {
        return personne;
    }

    public BooleanFilter personne() {
        if (personne == null) {
            personne = new BooleanFilter();
        }
        return personne;
    }

    public void setPersonne(BooleanFilter personne) {
        this.personne = personne;
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

    public StringFilter getNinea() {
        return ninea;
    }

    public StringFilter ninea() {
        if (ninea == null) {
            ninea = new StringFilter();
        }
        return ninea;
    }

    public void setNinea(StringFilter ninea) {
        this.ninea = ninea;
    }

    public DoubleFilter getMontant() {
        return montant;
    }

    public DoubleFilter montant() {
        if (montant == null) {
            montant = new DoubleFilter();
        }
        return montant;
    }

    public void setMontant(DoubleFilter montant) {
        this.montant = montant;
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
        final LocataireCriteria that = (LocataireCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(personne, that.personne) &&
            Objects.equals(activite, that.activite) &&
            Objects.equals(ninea, that.ninea) &&
            Objects.equals(montant, that.montant) &&
            Objects.equals(dossierId, that.dossierId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, personne, activite, ninea, montant, dossierId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocataireCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nom != null ? "nom=" + nom + ", " : "") +
            (personne != null ? "personne=" + personne + ", " : "") +
            (activite != null ? "activite=" + activite + ", " : "") +
            (ninea != null ? "ninea=" + ninea + ", " : "") +
            (montant != null ? "montant=" + montant + ", " : "") +
            (dossierId != null ? "dossierId=" + dossierId + ", " : "") +
            "}";
    }
}
