package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CategorieNature.
 */
@Entity
@Table(name = "categorie_nature")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CategorieNature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nature")
    private String nature;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "prix_metre_care")
    private Double prixMetreCare;

    @OneToMany(mappedBy = "categorieNature")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categorieNature", "dossier" }, allowSetters = true)
    private Set<EvaluationBatiments> evaluationBatiments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategorieNature id(Long id) {
        this.id = id;
        return this;
    }

    public String getNature() {
        return this.nature;
    }

    public CategorieNature nature(String nature) {
        this.nature = nature;
        return this;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public CategorieNature libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrixMetreCare() {
        return this.prixMetreCare;
    }

    public CategorieNature prixMetreCare(Double prixMetreCare) {
        this.prixMetreCare = prixMetreCare;
        return this;
    }

    public void setPrixMetreCare(Double prixMetreCare) {
        this.prixMetreCare = prixMetreCare;
    }

    public Set<EvaluationBatiments> getEvaluationBatiments() {
        return this.evaluationBatiments;
    }

    public CategorieNature evaluationBatiments(Set<EvaluationBatiments> evaluationBatiments) {
        this.setEvaluationBatiments(evaluationBatiments);
        return this;
    }

    public CategorieNature addEvaluationBatiments(EvaluationBatiments evaluationBatiments) {
        this.evaluationBatiments.add(evaluationBatiments);
        evaluationBatiments.setCategorieNature(this);
        return this;
    }

    public CategorieNature removeEvaluationBatiments(EvaluationBatiments evaluationBatiments) {
        this.evaluationBatiments.remove(evaluationBatiments);
        evaluationBatiments.setCategorieNature(null);
        return this;
    }

    public void setEvaluationBatiments(Set<EvaluationBatiments> evaluationBatiments) {
        if (this.evaluationBatiments != null) {
            this.evaluationBatiments.forEach(i -> i.setCategorieNature(null));
        }
        if (evaluationBatiments != null) {
            evaluationBatiments.forEach(i -> i.setCategorieNature(this));
        }
        this.evaluationBatiments = evaluationBatiments;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieNature)) {
            return false;
        }
        return id != null && id.equals(((CategorieNature) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieNature{" +
            "id=" + getId() +
            ", nature='" + getNature() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", prixMetreCare=" + getPrixMetreCare() +
            "}";
    }
}
