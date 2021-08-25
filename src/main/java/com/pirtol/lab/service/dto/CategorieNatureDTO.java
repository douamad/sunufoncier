package com.pirtol.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pirtol.lab.domain.CategorieNature} entity.
 */
public class CategorieNatureDTO implements Serializable {

    private Long id;

    private String nature;

    private String libelle;

    private Double prixMetreCare;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrixMetreCare() {
        return prixMetreCare;
    }

    public void setPrixMetreCare(Double prixMetreCare) {
        this.prixMetreCare = prixMetreCare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieNatureDTO)) {
            return false;
        }

        CategorieNatureDTO categorieNatureDTO = (CategorieNatureDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categorieNatureDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieNatureDTO{" +
            "id=" + getId() +
            ", nature='" + getNature() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", prixMetreCare=" + getPrixMetreCare() +
            "}";
    }
}
