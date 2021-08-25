package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EvaluationCoursAmenage.
 */
@Entity
@Table(name = "evaluation_cours_amenage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EvaluationCoursAmenage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "surface")
    private Double surface;

    @Column(name = "coeff")
    private Double coeff;

    @ManyToOne
    @JsonIgnoreProperties(value = { "evaluationCoursAmenages" }, allowSetters = true)
    private CategorieCoursAmenage categorieCoursAmenage;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "evaluationSurfaceBaties",
            "evaluationBatiments",
            "evaluationClotures",
            "evaluationCoursAmenages",
            "locataires",
            "dossier",
            "usageDossier",
            "proprietaire",
            "refParcelaire",
            "refcadastrale",
        },
        allowSetters = true
    )
    private Dossier dossier;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EvaluationCoursAmenage id(Long id) {
        this.id = id;
        return this;
    }

    public Double getSurface() {
        return this.surface;
    }

    public EvaluationCoursAmenage surface(Double surface) {
        this.surface = surface;
        return this;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }

    public Double getCoeff() {
        return this.coeff;
    }

    public EvaluationCoursAmenage coeff(Double coeff) {
        this.coeff = coeff;
        return this;
    }

    public void setCoeff(Double coeff) {
        this.coeff = coeff;
    }

    public CategorieCoursAmenage getCategorieCoursAmenage() {
        return this.categorieCoursAmenage;
    }

    public EvaluationCoursAmenage categorieCoursAmenage(CategorieCoursAmenage categorieCoursAmenage) {
        this.setCategorieCoursAmenage(categorieCoursAmenage);
        return this;
    }

    public void setCategorieCoursAmenage(CategorieCoursAmenage categorieCoursAmenage) {
        this.categorieCoursAmenage = categorieCoursAmenage;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public EvaluationCoursAmenage dossier(Dossier dossier) {
        this.setDossier(dossier);
        return this;
    }

    public void setDossier(Dossier dossier) {
        this.dossier = dossier;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EvaluationCoursAmenage)) {
            return false;
        }
        return id != null && id.equals(((EvaluationCoursAmenage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationCoursAmenage{" +
            "id=" + getId() +
            ", surface=" + getSurface() +
            ", coeff=" + getCoeff() +
            "}";
    }
}
