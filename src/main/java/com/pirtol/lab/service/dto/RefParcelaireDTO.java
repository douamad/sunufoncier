package com.pirtol.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pirtol.lab.domain.RefParcelaire} entity.
 */
public class RefParcelaireDTO implements Serializable {

    private Long id;

    private String numeroParcelle;

    private String natureParcelle;

    private Boolean batie;

    private CommuneDTO commune;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroParcelle() {
        return numeroParcelle;
    }

    public void setNumeroParcelle(String numeroParcelle) {
        this.numeroParcelle = numeroParcelle;
    }

    public String getNatureParcelle() {
        return natureParcelle;
    }

    public void setNatureParcelle(String natureParcelle) {
        this.natureParcelle = natureParcelle;
    }

    public Boolean getBatie() {
        return batie;
    }

    public void setBatie(Boolean batie) {
        this.batie = batie;
    }

    public CommuneDTO getCommune() {
        return commune;
    }

    public void setCommune(CommuneDTO commune) {
        this.commune = commune;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RefParcelaireDTO)) {
            return false;
        }

        RefParcelaireDTO refParcelaireDTO = (RefParcelaireDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, refParcelaireDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RefParcelaireDTO{" +
            "id=" + getId() +
            ", numeroParcelle='" + getNumeroParcelle() + "'" +
            ", natureParcelle='" + getNatureParcelle() + "'" +
            ", batie='" + getBatie() + "'" +
            ", commune=" + getCommune() +
            "}";
    }
}
