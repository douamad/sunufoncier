package com.pirtol.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pirtol.lab.domain.Arrondissement} entity.
 */
public class ArrondissementDTO implements Serializable {

    private Long id;

    private String code;

    private String libelle;

    private DepartementDTO departement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public DepartementDTO getDepartement() {
        return departement;
    }

    public void setDepartement(DepartementDTO departement) {
        this.departement = departement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArrondissementDTO)) {
            return false;
        }

        ArrondissementDTO arrondissementDTO = (ArrondissementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, arrondissementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArrondissementDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", departement=" + getDepartement() +
            "}";
    }
}
