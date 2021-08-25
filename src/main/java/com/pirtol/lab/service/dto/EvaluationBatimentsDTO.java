package com.pirtol.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pirtol.lab.domain.EvaluationBatiments} entity.
 */
public class EvaluationBatimentsDTO implements Serializable {

    private Long id;

    private Integer niveau;

    private Double surface;

    private Double coeff;

    private CategorieNatureDTO categorieNature;

    private DossierDTO dossier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNiveau() {
        return niveau;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

    public Double getSurface() {
        return surface;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }

    public Double getCoeff() {
        return coeff;
    }

    public void setCoeff(Double coeff) {
        this.coeff = coeff;
    }

    public CategorieNatureDTO getCategorieNature() {
        return categorieNature;
    }

    public void setCategorieNature(CategorieNatureDTO categorieNature) {
        this.categorieNature = categorieNature;
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
        if (!(o instanceof EvaluationBatimentsDTO)) {
            return false;
        }

        EvaluationBatimentsDTO evaluationBatimentsDTO = (EvaluationBatimentsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, evaluationBatimentsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationBatimentsDTO{" +
            "id=" + getId() +
            ", niveau=" + getNiveau() +
            ", surface=" + getSurface() +
            ", coeff=" + getCoeff() +
            ", categorieNature=" + getCategorieNature() +
            ", dossier=" + getDossier() +
            "}";
    }
}
