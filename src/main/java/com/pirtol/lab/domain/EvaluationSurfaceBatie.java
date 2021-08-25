package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EvaluationSurfaceBatie.
 */
@Entity
@Table(name = "evaluation_surface_batie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EvaluationSurfaceBatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "superficie_totale")
    private Double superficieTotale;

    @Column(name = "superficie_batie")
    private Double superficieBatie;

    @ManyToOne
    @JsonIgnoreProperties(value = { "evaluationSurfaceBaties" }, allowSetters = true)
    private CategorieBatie categorieBatie;

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

    public EvaluationSurfaceBatie id(Long id) {
        this.id = id;
        return this;
    }

    public Double getSuperficieTotale() {
        return this.superficieTotale;
    }

    public EvaluationSurfaceBatie superficieTotale(Double superficieTotale) {
        this.superficieTotale = superficieTotale;
        return this;
    }

    public void setSuperficieTotale(Double superficieTotale) {
        this.superficieTotale = superficieTotale;
    }

    public Double getSuperficieBatie() {
        return this.superficieBatie;
    }

    public EvaluationSurfaceBatie superficieBatie(Double superficieBatie) {
        this.superficieBatie = superficieBatie;
        return this;
    }

    public void setSuperficieBatie(Double superficieBatie) {
        this.superficieBatie = superficieBatie;
    }

    public CategorieBatie getCategorieBatie() {
        return this.categorieBatie;
    }

    public EvaluationSurfaceBatie categorieBatie(CategorieBatie categorieBatie) {
        this.setCategorieBatie(categorieBatie);
        return this;
    }

    public void setCategorieBatie(CategorieBatie categorieBatie) {
        this.categorieBatie = categorieBatie;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public EvaluationSurfaceBatie dossier(Dossier dossier) {
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
        if (!(o instanceof EvaluationSurfaceBatie)) {
            return false;
        }
        return id != null && id.equals(((EvaluationSurfaceBatie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EvaluationSurfaceBatie{" +
            "id=" + getId() +
            ", superficieTotale=" + getSuperficieTotale() +
            ", superficieBatie=" + getSuperficieBatie() +
            "}";
    }
}
