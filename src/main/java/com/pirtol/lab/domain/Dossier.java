package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Dossier.
 */
@Entity
@Table(name = "dossier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Dossier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "valeur_batie")
    private Double valeurBatie;

    @Column(name = "valeur_venale")
    private Double valeurVenale;

    @Column(name = "valeur_locativ")
    private Double valeurLocativ;

    @Column(name = "activite")
    private String activite;

    @OneToMany(mappedBy = "dossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categorieBatie", "dossier" }, allowSetters = true)
    private Set<EvaluationSurfaceBatie> evaluationSurfaceBaties = new HashSet<>();

    @OneToMany(mappedBy = "dossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categorieNature", "dossier" }, allowSetters = true)
    private Set<EvaluationBatiments> evaluationBatiments = new HashSet<>();

    @OneToMany(mappedBy = "dossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categoriteCloture", "dossier" }, allowSetters = true)
    private Set<EvaluationCloture> evaluationClotures = new HashSet<>();

    @OneToMany(mappedBy = "dossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "categorieCoursAmenage", "dossier" }, allowSetters = true)
    private Set<EvaluationCoursAmenage> evaluationCoursAmenages = new HashSet<>();

    @OneToMany(mappedBy = "dossier")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dossier" }, allowSetters = true)
    private Set<Locataire> locataires = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "lotissements", "quartier" }, allowSetters = true)
    private Lotissement dossier;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dossiers" }, allowSetters = true)
    private UsageDossier usageDossier;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dossiers", "representants" }, allowSetters = true)
    private Proprietaire proprietaire;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dossiers", "commune" }, allowSetters = true)
    private RefParcelaire refParcelaire;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dossiers" }, allowSetters = true)
    private Refcadastrale refcadastrale;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dossier id(Long id) {
        this.id = id;
        return this;
    }

    public String getNumero() {
        return this.numero;
    }

    public Dossier numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Double getValeurBatie() {
        return this.valeurBatie;
    }

    public Dossier valeurBatie(Double valeurBatie) {
        this.valeurBatie = valeurBatie;
        return this;
    }

    public void setValeurBatie(Double valeurBatie) {
        this.valeurBatie = valeurBatie;
    }

    public Double getValeurVenale() {
        return this.valeurVenale;
    }

    public Dossier valeurVenale(Double valeurVenale) {
        this.valeurVenale = valeurVenale;
        return this;
    }

    public void setValeurVenale(Double valeurVenale) {
        this.valeurVenale = valeurVenale;
    }

    public Double getValeurLocativ() {
        return this.valeurLocativ;
    }

    public Dossier valeurLocativ(Double valeurLocativ) {
        this.valeurLocativ = valeurLocativ;
        return this;
    }

    public void setValeurLocativ(Double valeurLocativ) {
        this.valeurLocativ = valeurLocativ;
    }

    public String getActivite() {
        return this.activite;
    }

    public Dossier activite(String activite) {
        this.activite = activite;
        return this;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public Set<EvaluationSurfaceBatie> getEvaluationSurfaceBaties() {
        return this.evaluationSurfaceBaties;
    }

    public Dossier evaluationSurfaceBaties(Set<EvaluationSurfaceBatie> evaluationSurfaceBaties) {
        this.setEvaluationSurfaceBaties(evaluationSurfaceBaties);
        return this;
    }

    public Dossier addEvaluationSurfaceBatie(EvaluationSurfaceBatie evaluationSurfaceBatie) {
        this.evaluationSurfaceBaties.add(evaluationSurfaceBatie);
        evaluationSurfaceBatie.setDossier(this);
        return this;
    }

    public Dossier removeEvaluationSurfaceBatie(EvaluationSurfaceBatie evaluationSurfaceBatie) {
        this.evaluationSurfaceBaties.remove(evaluationSurfaceBatie);
        evaluationSurfaceBatie.setDossier(null);
        return this;
    }

    public void setEvaluationSurfaceBaties(Set<EvaluationSurfaceBatie> evaluationSurfaceBaties) {
        if (this.evaluationSurfaceBaties != null) {
            this.evaluationSurfaceBaties.forEach(i -> i.setDossier(null));
        }
        if (evaluationSurfaceBaties != null) {
            evaluationSurfaceBaties.forEach(i -> i.setDossier(this));
        }
        this.evaluationSurfaceBaties = evaluationSurfaceBaties;
    }

    public Set<EvaluationBatiments> getEvaluationBatiments() {
        return this.evaluationBatiments;
    }

    public Dossier evaluationBatiments(Set<EvaluationBatiments> evaluationBatiments) {
        this.setEvaluationBatiments(evaluationBatiments);
        return this;
    }

    public Dossier addEvaluationBatiments(EvaluationBatiments evaluationBatiments) {
        this.evaluationBatiments.add(evaluationBatiments);
        evaluationBatiments.setDossier(this);
        return this;
    }

    public Dossier removeEvaluationBatiments(EvaluationBatiments evaluationBatiments) {
        this.evaluationBatiments.remove(evaluationBatiments);
        evaluationBatiments.setDossier(null);
        return this;
    }

    public void setEvaluationBatiments(Set<EvaluationBatiments> evaluationBatiments) {
        if (this.evaluationBatiments != null) {
            this.evaluationBatiments.forEach(i -> i.setDossier(null));
        }
        if (evaluationBatiments != null) {
            evaluationBatiments.forEach(i -> i.setDossier(this));
        }
        this.evaluationBatiments = evaluationBatiments;
    }

    public Set<EvaluationCloture> getEvaluationClotures() {
        return this.evaluationClotures;
    }

    public Dossier evaluationClotures(Set<EvaluationCloture> evaluationClotures) {
        this.setEvaluationClotures(evaluationClotures);
        return this;
    }

    public Dossier addEvaluationCloture(EvaluationCloture evaluationCloture) {
        this.evaluationClotures.add(evaluationCloture);
        evaluationCloture.setDossier(this);
        return this;
    }

    public Dossier removeEvaluationCloture(EvaluationCloture evaluationCloture) {
        this.evaluationClotures.remove(evaluationCloture);
        evaluationCloture.setDossier(null);
        return this;
    }

    public void setEvaluationClotures(Set<EvaluationCloture> evaluationClotures) {
        if (this.evaluationClotures != null) {
            this.evaluationClotures.forEach(i -> i.setDossier(null));
        }
        if (evaluationClotures != null) {
            evaluationClotures.forEach(i -> i.setDossier(this));
        }
        this.evaluationClotures = evaluationClotures;
    }

    public Set<EvaluationCoursAmenage> getEvaluationCoursAmenages() {
        return this.evaluationCoursAmenages;
    }

    public Dossier evaluationCoursAmenages(Set<EvaluationCoursAmenage> evaluationCoursAmenages) {
        this.setEvaluationCoursAmenages(evaluationCoursAmenages);
        return this;
    }

    public Dossier addEvaluationCoursAmenage(EvaluationCoursAmenage evaluationCoursAmenage) {
        this.evaluationCoursAmenages.add(evaluationCoursAmenage);
        evaluationCoursAmenage.setDossier(this);
        return this;
    }

    public Dossier removeEvaluationCoursAmenage(EvaluationCoursAmenage evaluationCoursAmenage) {
        this.evaluationCoursAmenages.remove(evaluationCoursAmenage);
        evaluationCoursAmenage.setDossier(null);
        return this;
    }

    public void setEvaluationCoursAmenages(Set<EvaluationCoursAmenage> evaluationCoursAmenages) {
        if (this.evaluationCoursAmenages != null) {
            this.evaluationCoursAmenages.forEach(i -> i.setDossier(null));
        }
        if (evaluationCoursAmenages != null) {
            evaluationCoursAmenages.forEach(i -> i.setDossier(this));
        }
        this.evaluationCoursAmenages = evaluationCoursAmenages;
    }

    public Set<Locataire> getLocataires() {
        return this.locataires;
    }

    public Dossier locataires(Set<Locataire> locataires) {
        this.setLocataires(locataires);
        return this;
    }

    public Dossier addLocataire(Locataire locataire) {
        this.locataires.add(locataire);
        locataire.setDossier(this);
        return this;
    }

    public Dossier removeLocataire(Locataire locataire) {
        this.locataires.remove(locataire);
        locataire.setDossier(null);
        return this;
    }

    public void setLocataires(Set<Locataire> locataires) {
        if (this.locataires != null) {
            this.locataires.forEach(i -> i.setDossier(null));
        }
        if (locataires != null) {
            locataires.forEach(i -> i.setDossier(this));
        }
        this.locataires = locataires;
    }

    public Lotissement getDossier() {
        return this.dossier;
    }

    public Dossier dossier(Lotissement lotissement) {
        this.setDossier(lotissement);
        return this;
    }

    public void setDossier(Lotissement lotissement) {
        this.dossier = lotissement;
    }

    public UsageDossier getUsageDossier() {
        return this.usageDossier;
    }

    public Dossier usageDossier(UsageDossier usageDossier) {
        this.setUsageDossier(usageDossier);
        return this;
    }

    public void setUsageDossier(UsageDossier usageDossier) {
        this.usageDossier = usageDossier;
    }

    public Proprietaire getProprietaire() {
        return this.proprietaire;
    }

    public Dossier proprietaire(Proprietaire proprietaire) {
        this.setProprietaire(proprietaire);
        return this;
    }

    public void setProprietaire(Proprietaire proprietaire) {
        this.proprietaire = proprietaire;
    }

    public RefParcelaire getRefParcelaire() {
        return this.refParcelaire;
    }

    public Dossier refParcelaire(RefParcelaire refParcelaire) {
        this.setRefParcelaire(refParcelaire);
        return this;
    }

    public void setRefParcelaire(RefParcelaire refParcelaire) {
        this.refParcelaire = refParcelaire;
    }

    public Refcadastrale getRefcadastrale() {
        return this.refcadastrale;
    }

    public Dossier refcadastrale(Refcadastrale refcadastrale) {
        this.setRefcadastrale(refcadastrale);
        return this;
    }

    public void setRefcadastrale(Refcadastrale refcadastrale) {
        this.refcadastrale = refcadastrale;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dossier)) {
            return false;
        }
        return id != null && id.equals(((Dossier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dossier{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", valeurBatie=" + getValeurBatie() +
            ", valeurVenale=" + getValeurVenale() +
            ", valeurLocativ=" + getValeurLocativ() +
            ", activite='" + getActivite() + "'" +
            "}";
    }
}
