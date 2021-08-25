package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CategorieCoursAmenage.
 */
@Entity
@Table(name = "categorie_cours_amenage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CategorieCoursAmenage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "prix_metre_care")
    private Double prixMetreCare;

    @OneToMany(mappedBy = "categorieCoursAmenage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categorieCoursAmenage", "dossier" }, allowSetters = true)
    private Set<EvaluationCoursAmenage> evaluationCoursAmenages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategorieCoursAmenage id(Long id) {
        this.id = id;
        return this;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public CategorieCoursAmenage libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrixMetreCare() {
        return this.prixMetreCare;
    }

    public CategorieCoursAmenage prixMetreCare(Double prixMetreCare) {
        this.prixMetreCare = prixMetreCare;
        return this;
    }

    public void setPrixMetreCare(Double prixMetreCare) {
        this.prixMetreCare = prixMetreCare;
    }

    public Set<EvaluationCoursAmenage> getEvaluationCoursAmenages() {
        return this.evaluationCoursAmenages;
    }

    public CategorieCoursAmenage evaluationCoursAmenages(Set<EvaluationCoursAmenage> evaluationCoursAmenages) {
        this.setEvaluationCoursAmenages(evaluationCoursAmenages);
        return this;
    }

    public CategorieCoursAmenage addEvaluationCoursAmenage(EvaluationCoursAmenage evaluationCoursAmenage) {
        this.evaluationCoursAmenages.add(evaluationCoursAmenage);
        evaluationCoursAmenage.setCategorieCoursAmenage(this);
        return this;
    }

    public CategorieCoursAmenage removeEvaluationCoursAmenage(EvaluationCoursAmenage evaluationCoursAmenage) {
        this.evaluationCoursAmenages.remove(evaluationCoursAmenage);
        evaluationCoursAmenage.setCategorieCoursAmenage(null);
        return this;
    }

    public void setEvaluationCoursAmenages(Set<EvaluationCoursAmenage> evaluationCoursAmenages) {
        if (this.evaluationCoursAmenages != null) {
            this.evaluationCoursAmenages.forEach(i -> i.setCategorieCoursAmenage(null));
        }
        if (evaluationCoursAmenages != null) {
            evaluationCoursAmenages.forEach(i -> i.setCategorieCoursAmenage(this));
        }
        this.evaluationCoursAmenages = evaluationCoursAmenages;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieCoursAmenage)) {
            return false;
        }
        return id != null && id.equals(((CategorieCoursAmenage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieCoursAmenage{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", prixMetreCare=" + getPrixMetreCare() +
            "}";
    }
}
