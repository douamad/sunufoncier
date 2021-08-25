package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Departement.
 */
@Entity
@Table(name = "departement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Departement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "libelle")
    private String libelle;

    @OneToMany(mappedBy = "departement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "communes", "departement" }, allowSetters = true)
    private Set<Arrondissement> arrondissements = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "departements" }, allowSetters = true)
    private Region region;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Departement id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public Departement code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Departement libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Arrondissement> getArrondissements() {
        return this.arrondissements;
    }

    public Departement arrondissements(Set<Arrondissement> arrondissements) {
        this.setArrondissements(arrondissements);
        return this;
    }

    public Departement addArrondissement(Arrondissement arrondissement) {
        this.arrondissements.add(arrondissement);
        arrondissement.setDepartement(this);
        return this;
    }

    public Departement removeArrondissement(Arrondissement arrondissement) {
        this.arrondissements.remove(arrondissement);
        arrondissement.setDepartement(null);
        return this;
    }

    public void setArrondissements(Set<Arrondissement> arrondissements) {
        if (this.arrondissements != null) {
            this.arrondissements.forEach(i -> i.setDepartement(null));
        }
        if (arrondissements != null) {
            arrondissements.forEach(i -> i.setDepartement(this));
        }
        this.arrondissements = arrondissements;
    }

    public Region getRegion() {
        return this.region;
    }

    public Departement region(Region region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Departement)) {
            return false;
        }
        return id != null && id.equals(((Departement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Departement{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
