package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Locataire.
 */
@Entity
@Table(name = "locataire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Locataire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "personne")
    private Boolean personne;

    @Column(name = "activite")
    private String activite;

    @Column(name = "ninea")
    private String ninea;

    @Column(name = "montant")
    private Double montant;

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

    public Locataire id(Long id) {
        this.id = id;
        return this;
    }

    public String getNom() {
        return this.nom;
    }

    public Locataire nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getPersonne() {
        return this.personne;
    }

    public Locataire personne(Boolean personne) {
        this.personne = personne;
        return this;
    }

    public void setPersonne(Boolean personne) {
        this.personne = personne;
    }

    public String getActivite() {
        return this.activite;
    }

    public Locataire activite(String activite) {
        this.activite = activite;
        return this;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public String getNinea() {
        return this.ninea;
    }

    public Locataire ninea(String ninea) {
        this.ninea = ninea;
        return this;
    }

    public void setNinea(String ninea) {
        this.ninea = ninea;
    }

    public Double getMontant() {
        return this.montant;
    }

    public Locataire montant(Double montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Dossier getDossier() {
        return this.dossier;
    }

    public Locataire dossier(Dossier dossier) {
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
        if (!(o instanceof Locataire)) {
            return false;
        }
        return id != null && id.equals(((Locataire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Locataire{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", personne='" + getPersonne() + "'" +
            ", activite='" + getActivite() + "'" +
            ", ninea='" + getNinea() + "'" +
            ", montant=" + getMontant() +
            "}";
    }
}
