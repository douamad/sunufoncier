package com.pirtol.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pirtol.lab.domain.EvaluationCloture} entity.
 */
public class EvaluationClotureDTO implements Serializable {

    private Long id;

    private Double lineaire;

    private Double coeff;

    private CategorieClotureDTO categoriteCloture;

    private DossierDTO dossier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLineaire() {
        return lineaire;
    }

    public void setLineaire(Double lineaire) {
        this.lineaire = lineaire;
    }

    public Double getCoeff() {
        return coeff;
    }

    public void setCoeff(Double coeff) {
        this.coeff = coeff;
    }

    public CategorieClotureDTO getCategoriteCloture() {
        return categoriteCloture;
    }

    public void setCategoriteCloture(CategorieClotureDTO categoriteCloture) {
        this.categoriteCloture = categoriteCloture;
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
        if (!(o instanceof EvaluationClotureDTO)) {
            return false;
        }

        EvaluationClotureDTO evaluationClotureDTO = (EvaluationClotureDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, evaluationClotureDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationClotureDTO{" +
            "id=" + getId() +
            ", lineaire=" + getLineaire() +
            ", coeff=" + getCoeff() +
            ", categoriteCloture=" + getCategoriteCloture() +
            ", dossier=" + getDossier() +
            "}";
    }
}
