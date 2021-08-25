package com.pirtol.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pirtol.lab.domain.Quartier} entity.
 */
public class QuartierDTO implements Serializable {

    private Long id;

    private String code;

    private String libelle;

    private CommuneDTO communune;

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

    public CommuneDTO getCommunune() {
        return communune;
    }

    public void setCommunune(CommuneDTO communune) {
        this.communune = communune;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuartierDTO)) {
            return false;
        }

        QuartierDTO quartierDTO = (QuartierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, quartierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuartierDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", communune=" + getCommunune() +
            "}";
    }
}
