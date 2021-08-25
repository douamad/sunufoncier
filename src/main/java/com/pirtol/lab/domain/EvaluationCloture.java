package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EvaluationCloture.
 */
@Entity
@Table(name = "evaluation_cloture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EvaluationCloture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "lineaire")
    private Double lineaire;

    @Column(name = "coeff")
    private Double coeff;

    @ManyToOne
    @JsonIgnoreProperties(value = { "evaluationClotures" }, allowSetters = true)
    private CategorieCloture categoriteCloture;

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

    public EvaluationCloture id(Long id) {
        this.id = id;
        return this;
    }

    public Double getLineaire() {
        return this.lineaire;
    }

    public EvaluationCloture lineaire(Double lineaire) {
        this.lineaire = lineaire;
        return this;
    }

    public void setLineaire(Double lineaire) {
        this.lineaire = lineaire;
    }

    public Double getCoeff() {
        return this.coeff;
    }

    public EvaluationCloture coeff(Double coeff) {
        this.coeff = coeff;
        return this;
    }

    public void setCoeff(Double coeff) {
        this.coeff = coeff;
    }

    public CategorieCloture getCategoriteCloture() {
        return this.categoriteCloture;
    }

    public EvaluationCloture categoriteCloture(CategorieCloture categorieCloture) {
        this.setCategoriteCloture(categorieCloture);
        return this;
    }

    public void setCategoriteCloture(CategorieCloture categorieCloture) {
        this.categoriteCloture = categorieCloture;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public EvaluationCloture dossier(Dossier dossier) {
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
        if (!(o instanceof EvaluationCloture)) {
            return false;
        }
        return id != null && id.equals(((EvaluationCloture) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationCloture{" +
            "id=" + getId() +
            ", lineaire=" + getLineaire() +
            ", coeff=" + getCoeff() +
            "}";
    }
}
