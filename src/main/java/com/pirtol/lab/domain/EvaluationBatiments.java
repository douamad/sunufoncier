package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EvaluationBatiments.
 */
@Entity
@Table(name = "evaluation_batiments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EvaluationBatiments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "niveau")
    private Integer niveau;

    @Column(name = "surface")
    private Double surface;

    @Column(name = "coeff")
    private Double coeff;

    @ManyToOne
    @JsonIgnoreProperties(value = { "evaluationBatiments" }, allowSetters = true)
    private CategorieNature categorieNature;

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

    public EvaluationBatiments id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNiveau() {
        return this.niveau;
    }

    public EvaluationBatiments niveau(Integer niveau) {
        this.niveau = niveau;
        return this;
    }

    public void setNiveau(Integer niveau) {
        this.niveau = niveau;
    }

    public Double getSurface() {
        return this.surface;
    }

    public EvaluationBatiments surface(Double surface) {
        this.surface = surface;
        return this;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }

    public Double getCoeff() {
        return this.coeff;
    }

    public EvaluationBatiments coeff(Double coeff) {
        this.coeff = coeff;
        return this;
    }

    public void setCoeff(Double coeff) {
        this.coeff = coeff;
    }

    public CategorieNature getCategorieNature() {
        return this.categorieNature;
    }

    public EvaluationBatiments categorieNature(CategorieNature categorieNature) {
        this.setCategorieNature(categorieNature);
        return this;
    }

    public void setCategorieNature(CategorieNature categorieNature) {
        this.categorieNature = categorieNature;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public EvaluationBatiments dossier(Dossier dossier) {
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
        if (!(o instanceof EvaluationBatiments)) {
            return false;
        }
        return id != null && id.equals(((EvaluationBatiments) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationBatiments{" +
            "id=" + getId() +
            ", niveau=" + getNiveau() +
            ", surface=" + getSurface() +
            ", coeff=" + getCoeff() +
            "}";
    }
}
