package com.pirtol.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pirtol.lab.domain.Locataire} entity.
 */
public class LocataireDTO implements Serializable {

    private Long id;

    private String nom;

    private Boolean personne;

    private String activite;

    private String ninea;

    private Double montant;

    private DossierDTO dossier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getPersonne() {
        return personne;
    }

    public void setPersonne(Boolean personne) {
        this.personne = personne;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getNinea() {
        return ninea;
    }

    public void setNinea(String ninea) {
        this.ninea = ninea;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public DossierDTO getDossier() {
        return dossier;
    }

    public void setDossier(DossierDTO dossier) {
        this.dossier = dossier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocataireDTO)) {
            return false;
        }

        LocataireDTO locataireDTO = (LocataireDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, locataireDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocataireDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", personne='" + getPersonne() + "'" +
            ", activite='" + getActivite() + "'" +
            ", ninea='" + getNinea() + "'" +
            ", montant=" + getMontant() +
            ", dossier=" + getDossier() +
            "}";
    }
}
