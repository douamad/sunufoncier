package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RefParcelaire.
 */
@Entity
@Table(name = "ref_parcelaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RefParcelaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero_parcelle")
    private String numeroParcelle;

    @Column(name = "nature_parcelle")
    private String natureParcelle;

    @Column(name = "batie")
    private Boolean batie;

    @OneToMany(mappedBy = "refParcelaire")
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
    private Set<Dossier> dossiers = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "quartiers", "arrondissement", "refParcelaires" }, allowSetters = true)
    private Commune commune;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RefParcelaire id(Long id) {
        this.id = id;
        return this;
    }

    public String getNumeroParcelle() {
        return this.numeroParcelle;
    }

    public RefParcelaire numeroParcelle(String numeroParcelle) {
        this.numeroParcelle = numeroParcelle;
        return this;
    }

    public void setNumeroParcelle(String numeroParcelle) {
        this.numeroParcelle = numeroParcelle;
    }

    public String getNatureParcelle() {
        return this.natureParcelle;
    }

    public RefParcelaire natureParcelle(String natureParcelle) {
        this.natureParcelle = natureParcelle;
        return this;
    }

    public void setNatureParcelle(String natureParcelle) {
        this.natureParcelle = natureParcelle;
    }

    public Boolean getBatie() {
        return this.batie;
    }

    public RefParcelaire batie(Boolean batie) {
        this.batie = batie;
        return this;
    }

    public void setBatie(Boolean batie) {
        this.batie = batie;
    }

    public Set<Dossier> getDossiers() {
        return this.dossiers;
    }

    public RefParcelaire dossiers(Set<Dossier> dossiers) {
        this.setDossiers(dossiers);
        return this;
    }

    public RefParcelaire addDossier(Dossier dossier) {
        this.dossiers.add(dossier);
        dossier.setRefParcelaire(this);
        return this;
    }

    public RefParcelaire removeDossier(Dossier dossier) {
        this.dossiers.remove(dossier);
        dossier.setRefParcelaire(null);
        return this;
    }

    public void setDossiers(Set<Dossier> dossiers) {
        if (this.dossiers != null) {
            this.dossiers.forEach(i -> i.setRefParcelaire(null));
        }
        if (dossiers != null) {
            dossiers.forEach(i -> i.setRefParcelaire(this));
        }
        this.dossiers = dossiers;
    }

    public Commune getCommune() {
        return this.commune;
    }

    public RefParcelaire commune(Commune commune) {
        this.setCommune(commune);
        return this;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RefParcelaire)) {
            return false;
        }
        return id != null && id.equals(((RefParcelaire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RefParcelaire{" +
            "id=" + getId() +
            ", numeroParcelle='" + getNumeroParcelle() + "'" +
            ", natureParcelle='" + getNatureParcelle() + "'" +
            ", batie='" + getBatie() + "'" +
            "}";
    }
}
