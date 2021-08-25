package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CategorieCloture.
 */
@Entity
@Table(name = "categorie_cloture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CategorieCloture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "prix_metre_care")
    private Double prixMetreCare;

    @OneToMany(mappedBy = "categoriteCloture")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categoriteCloture", "dossier" }, allowSetters = true)
    private Set<EvaluationCloture> evaluationClotures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategorieCloture id(Long id) {
        this.id = id;
        return this;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public CategorieCloture libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrixMetreCare() {
        return this.prixMetreCare;
    }

    public CategorieCloture prixMetreCare(Double prixMetreCare) {
        this.prixMetreCare = prixMetreCare;
        return this;
    }

    public void setPrixMetreCare(Double prixMetreCare) {
        this.prixMetreCare = prixMetreCare;
    }

    public Set<EvaluationCloture> getEvaluationClotures() {
        return this.evaluationClotures;
    }

    public CategorieCloture evaluationClotures(Set<EvaluationCloture> evaluationClotures) {
        this.setEvaluationClotures(evaluationClotures);
        return this;
    }

    public CategorieCloture addEvaluationCloture(EvaluationCloture evaluationCloture) {
        this.evaluationClotures.add(evaluationCloture);
        evaluationCloture.setCategoriteCloture(this);
        return this;
    }

    public CategorieCloture removeEvaluationCloture(EvaluationCloture evaluationCloture) {
        this.evaluationClotures.remove(evaluationCloture);
        evaluationCloture.setCategoriteCloture(null);
        return this;
    }

    public void setEvaluationClotures(Set<EvaluationCloture> evaluationClotures) {
        if (this.evaluationClotures != null) {
            this.evaluationClotures.forEach(i -> i.setCategoriteCloture(null));
        }
        if (evaluationClotures != null) {
            evaluationClotures.forEach(i -> i.setCategoriteCloture(this));
        }
        this.evaluationClotures = evaluationClotures;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieCloture)) {
            return false;
        }
        return id != null && id.equals(((CategorieCloture) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieCloture{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", prixMetreCare=" + getPrixMetreCare() +
            "}";
    }
}
