package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Lotissement.
 */
@Entity
@Table(name = "lotissement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Lotissement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "dossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Dossier> lotissements = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "lotissements", "communune" }, allowSetters = true)
    private Quartier quartier;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Lotissement id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Lotissement code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Lotissement libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Dossier> getLotissements() {
        return this.lotissements;
    }

    public Lotissement lotissements(Set<Dossier> dossiers) {
        this.setLotissements(dossiers);
        return this;
    }

    public Lotissement addLotissement(Dossier dossier) {
        this.lotissements.add(dossier);
        dossier.setDossier(this);
        return this;
    }

    public Lotissement removeLotissement(Dossier dossier) {
        this.lotissements.remove(dossier);
        dossier.setDossier(null);
        return this;
    }

    public void setLotissements(Set<Dossier> dossiers) {
        if (this.lotissements != null) {
            this.lotissements.forEach(i -> i.setDossier(null));
        }
        if (dossiers != null) {
            dossiers.forEach(i -> i.setDossier(this));
        }
        this.lotissements = dossiers;
    }

    public Quartier getQuartier() {
        return this.quartier;
    }

    public Lotissement quartier(Quartier quartier) {
        this.setQuartier(quartier);
        return this;
    }

    public void setQuartier(Quartier quartier) {
        this.quartier = quartier;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lotissement)) {
            return false;
        }
        return id != null && id.equals(((Lotissement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lotissement{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
