package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CategorieBatie.
 */
@Entity
@Table(name = "categorie_batie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CategorieBatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "prix_metre_care")
    private Double prixMetreCare;

    @OneToMany(mappedBy = "categorieBatie")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categorieBatie", "dossier" }, allowSetters = true)
    private Set<EvaluationSurfaceBatie> evaluationSurfaceBaties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategorieBatie id(Long id) {
        this.id = id;
        return this;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public CategorieBatie libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrixMetreCare() {
        return this.prixMetreCare;
    }

    public CategorieBatie prixMetreCare(Double prixMetreCare) {
        this.prixMetreCare = prixMetreCare;
        return this;
    }

    public void setPrixMetreCare(Double prixMetreCare) {
        this.prixMetreCare = prixMetreCare;
    }

    public Set<EvaluationSurfaceBatie> getEvaluationSurfaceBaties() {
        return this.evaluationSurfaceBaties;
    }

    public CategorieBatie evaluationSurfaceBaties(Set<EvaluationSurfaceBatie> evaluationSurfaceBaties) {
        this.setEvaluationSurfaceBaties(evaluationSurfaceBaties);
        return this;
    }

    public CategorieBatie addEvaluationSurfaceBatie(EvaluationSurfaceBatie evaluationSurfaceBatie) {
        this.evaluationSurfaceBaties.add(evaluationSurfaceBatie);
        evaluationSurfaceBatie.setCategorieBatie(this);
        return this;
    }

    public CategorieBatie removeEvaluationSurfaceBatie(EvaluationSurfaceBatie evaluationSurfaceBatie) {
        this.evaluationSurfaceBaties.remove(evaluationSurfaceBatie);
        evaluationSurfaceBatie.setCategorieBatie(null);
        return this;
    }

    public void setEvaluationSurfaceBaties(Set<EvaluationSurfaceBatie> evaluationSurfaceBaties) {
        if (this.evaluationSurfaceBaties != null) {
            this.evaluationSurfaceBaties.forEach(i -> i.setCategorieBatie(null));
        }
        if (evaluationSurfaceBaties != null) {
            evaluationSurfaceBaties.forEach(i -> i.setCategorieBatie(this));
        }
        this.evaluationSurfaceBaties = evaluationSurfaceBaties;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieBatie)) {
            return false;
        }
        return id != null && id.equals(((CategorieBatie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieBatie{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", prixMetreCare=" + getPrixMetreCare() +
            "}";
    }
}
