package com.pirtol.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pirtol.lab.domain.Commune} entity.
 */
public class CommuneDTO implements Serializable {

    private Long id;

    private String code;

    private String libelle;

    private ArrondissementDTO arrondissement;

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

    public ArrondissementDTO getArrondissement() {
        return arrondissement;
    }

    public void setArrondissement(ArrondissementDTO arrondissement) {
        this.arrondissement = arrondissement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommuneDTO)) {
            return false;
        }

        CommuneDTO communeDTO = (CommuneDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, communeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommuneDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", arrondissement=" + getArrondissement() +
            "}";
    }
}
