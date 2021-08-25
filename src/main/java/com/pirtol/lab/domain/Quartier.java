package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Quartier.
 */
@Entity
@Table(name = "quartier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Quartier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "quartier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "lotissements", "quartier" }, allowSetters = true)
    private Set<Lotissement> lotissements = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "quartiers", "arrondissement", "refParcelaires" }, allowSetters = true)
    private Commune communune;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Quartier id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Quartier code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Quartier libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Lotissement> getLotissements() {
        return this.lotissements;
    }

    public Quartier lotissements(Set<Lotissement> lotissements) {
        this.setLotissements(lotissements);
        return this;
    }

    public Quartier addLotissement(Lotissement lotissement) {
        this.lotissements.add(lotissement);
        lotissement.setQuartier(this);
        return this;
    }

    public Quartier removeLotissement(Lotissement lotissement) {
        this.lotissements.remove(lotissement);
        lotissement.setQuartier(null);
        return this;
    }

    public void setLotissements(Set<Lotissement> lotissements) {
        if (this.lotissements != null) {
            this.lotissements.forEach(i -> i.setQuartier(null));
        }
        if (lotissements != null) {
            lotissements.forEach(i -> i.setQuartier(this));
        }
        this.lotissements = lotissements;
    }

    public Commune getCommunune() {
        return this.communune;
    }

    public Quartier communune(Commune commune) {
        this.setCommunune(commune);
        return this;
    }

    public void setCommunune(Commune commune) {
        this.communune = commune;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quartier)) {
            return false;
        }
        return id != null && id.equals(((Quartier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Quartier{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
