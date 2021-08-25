package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Commune.
 */
@Entity
@Table(name = "commune")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Commune implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "communune")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "lotissements", "communune" }, allowSetters = true)
    private Set<Quartier> quartiers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "communes", "departement" }, allowSetters = true)
    private Arrondissement arrondissement;

    @OneToMany(mappedBy = "commune")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dossiers", "commune" }, allowSetters = true)
    private Set<RefParcelaire> refParcelaires = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commune id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Commune code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Commune libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Quartier> getQuartiers() {
        return this.quartiers;
    }

    public Commune quartiers(Set<Quartier> quartiers) {
        this.setQuartiers(quartiers);
        return this;
    }

    public Commune addQuartier(Quartier quartier) {
        this.quartiers.add(quartier);
        quartier.setCommunune(this);
        return this;
    }

    public Commune removeQuartier(Quartier quartier) {
        this.quartiers.remove(quartier);
        quartier.setCommunune(null);
        return this;
    }

    public void setQuartiers(Set<Quartier> quartiers) {
        if (this.quartiers != null) {
            this.quartiers.forEach(i -> i.setCommunune(null));
        }
        if (quartiers != null) {
            quartiers.forEach(i -> i.setCommunune(this));
        }
        this.quartiers = quartiers;
    }

    public Arrondissement getArrondissement() {
        return this.arrondissement;
    }

    public Commune arrondissement(Arrondissement arrondissement) {
        this.setArrondissement(arrondissement);
        return this;
    }

    public void setArrondissement(Arrondissement arrondissement) {
        this.arrondissement = arrondissement;
    }

    public Set<RefParcelaire> getRefParcelaires() {
        return this.refParcelaires;
    }

    public Commune refParcelaires(Set<RefParcelaire> refParcelaires) {
        this.setRefParcelaires(refParcelaires);
        return this;
    }

    public Commune addRefParcelaire(RefParcelaire refParcelaire) {
        this.refParcelaires.add(refParcelaire);
        refParcelaire.setCommune(this);
        return this;
    }

    public Commune removeRefParcelaire(RefParcelaire refParcelaire) {
        this.refParcelaires.remove(refParcelaire);
        refParcelaire.setCommune(null);
        return this;
    }

    public void setRefParcelaires(Set<RefParcelaire> refParcelaires) {
        if (this.refParcelaires != null) {
            this.refParcelaires.forEach(i -> i.setCommune(null));
        }
        if (refParcelaires != null) {
            refParcelaires.forEach(i -> i.setCommune(this));
        }
        this.refParcelaires = refParcelaires;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commune)) {
            return false;
        }
        return id != null && id.equals(((Commune) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Commune{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
