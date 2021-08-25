package com.pirtol.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pirtol.lab.domain.EvaluationSurfaceBatie} entity.
 */
public class EvaluationSurfaceBatieDTO implements Serializable {

    private Long id;

    private Double superficieTotale;

    private Double superficieBatie;

    private CategorieBatieDTO categorieBatie;

    private DossierDTO dossier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSuperficieTotale() {
        return superficieTotale;
    }

    public void setSuperficieTotale(Double superficieTotale) {
        this.superficieTotale = superficieTotale;
    }

    public Double getSuperficieBatie() {
        return superficieBatie;
    }

    public void setSuperficieBatie(Double superficieBatie) {
        this.superficieBatie = superficieBatie;
    }

    public CategorieBatieDTO getCategorieBatie() {
        return categorieBatie;
    }

    public void setCategorieBatie(CategorieBatieDTO categorieBatie) {
        this.categorieBatie = categorieBatie;
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
        if (!(o instanceof EvaluationSurfaceBatieDTO)) {
            return false;
        }

        EvaluationSurfaceBatieDTO evaluationSurfaceBatieDTO = (EvaluationSurfaceBatieDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, evaluationSurfaceBatieDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationSurfaceBatieDTO{" +
            "id=" + getId() +
            ", superficieTotale=" + getSuperficieTotale() +
            ", superficieBatie=" + getSuperficieBatie() +
            ", categorieBatie=" + getCategorieBatie() +
            ", dossier=" + getDossier() +
            "}";
    }
}
