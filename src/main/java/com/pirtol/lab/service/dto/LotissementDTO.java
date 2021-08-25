package com.pirtol.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pirtol.lab.domain.Lotissement} entity.
 */
public class LotissementDTO implements Serializable {

    private Long id;

    private String code;

    private String libelle;

    private QuartierDTO quartier;

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

    public QuartierDTO getQuartier() {
        return quartier;
    }

    public void setQuartier(QuartierDTO quartier) {
        this.quartier = quartier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LotissementDTO)) {
            return false;
        }

        LotissementDTO lotissementDTO = (LotissementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, lotissementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LotissementDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", quartier=" + getQuartier() +
            "}";
    }
}
