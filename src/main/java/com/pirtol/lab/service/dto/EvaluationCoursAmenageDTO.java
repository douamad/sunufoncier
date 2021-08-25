package com.pirtol.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pirtol.lab.domain.EvaluationCoursAmenage} entity.
 */
public class EvaluationCoursAmenageDTO implements Serializable {

    private Long id;

    private Double surface;

    private Double coeff;

    private CategorieCoursAmenageDTO categorieCoursAmenage;

    private DossierDTO dossier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CategorieCoursAmenageDTO getCategorieCoursAmenage() {
        return categorieCoursAmenage;
    }

    public void setCategorieCoursAmenage(CategorieCoursAmenageDTO categorieCoursAmenage) {
        this.categorieCoursAmenage = categorieCoursAmenage;
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
        if (!(o instanceof EvaluationCoursAmenageDTO)) {
            return false;
        }

        EvaluationCoursAmenageDTO evaluationCoursAmenageDTO = (EvaluationCoursAmenageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, evaluationCoursAmenageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationCoursAmenageDTO{" +
            "id=" + getId() +
            ", surface=" + getSurface() +
            ", coeff=" + getCoeff() +
            ", categorieCoursAmenage=" + getCategorieCoursAmenage() +
            ", dossier=" + getDossier() +
            "}";
    }
}
