package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pirtol.lab.domain.enumeration.SituationProprietaire;
import com.pirtol.lab.domain.enumeration.TypeStructure;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Proprietaire.
 */
@Entity
@Table(name = "proprietaire")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Proprietaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "nom")
    private String nom;

    @Enumerated(EnumType.STRING)
    @Column(name = "situation")
    private SituationProprietaire situation;

    @Column(name = "gestion_delegue")
    private Boolean gestionDelegue;

    @Column(name = "raison_social")
    private String raisonSocial;

    @Column(name = "siege_social")
    private String siegeSocial;

    @Column(name = "personne_morale")
    private Boolean personneMorale;

    @Column(name = "date_naiss")
    private Instant dateNaiss;

    @Column(name = "lieu_naissance")
    private String lieuNaissance;

    @Column(name = "num_cni")
    private String numCNI;

    @Column(name = "ninea")
    private String ninea;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "email")
    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "telephone_2")
    private String telephone2;

    @Column(name = "telephone_3")
    private String telephone3;

    @Column(name = "aquisition")
    private String aquisition;

    @Column(name = "statut_persone_structure")
    private String statutPersoneStructure;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_structure")
    private TypeStructure typeStructure;

    @Column(name = "nombre_heritiers")
    private Integer nombreHeritiers;

    @Column(name = "service_ocupant")
    private String serviceOcupant;

    @Column(name = "etablssement")
    private String etablssement;

    @Column(name = "date_delivrance")
    private Instant dateDelivrance;

    @OneToMany(mappedBy = "proprietaire")
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

    @OneToMany(mappedBy = "proprietaire")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "proprietaire" }, allowSetters = true)
    private Set<Representant> representants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Proprietaire id(Long id) {
        this.id = id;
        return this;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Proprietaire prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return this.nom;
    }

    public Proprietaire nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public SituationProprietaire getSituation() {
        return this.situation;
    }

    public Proprietaire situation(SituationProprietaire situation) {
        this.situation = situation;
        return this;
    }

    public void setSituation(SituationProprietaire situation) {
        this.situation = situation;
    }

    public Boolean getGestionDelegue() {
        return this.gestionDelegue;
    }

    public Proprietaire gestionDelegue(Boolean gestionDelegue) {
        this.gestionDelegue = gestionDelegue;
        return this;
    }

    public void setGestionDelegue(Boolean gestionDelegue) {
        this.gestionDelegue = gestionDelegue;
    }

    public String getRaisonSocial() {
        return this.raisonSocial;
    }

    public Proprietaire raisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
        return this;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public String getSiegeSocial() {
        return this.siegeSocial;
    }

    public Proprietaire siegeSocial(String siegeSocial) {
        this.siegeSocial = siegeSocial;
        return this;
    }

    public void setSiegeSocial(String siegeSocial) {
        this.siegeSocial = siegeSocial;
    }

    public Boolean getPersonneMorale() {
        return this.personneMorale;
    }

    public Proprietaire personneMorale(Boolean personneMorale) {
        this.personneMorale = personneMorale;
        return this;
    }

    public void setPersonneMorale(Boolean personneMorale) {
        this.personneMorale = personneMorale;
    }

    public Instant getDateNaiss() {
        return this.dateNaiss;
    }

    public Proprietaire dateNaiss(Instant dateNaiss) {
        this.dateNaiss = dateNaiss;
        return this;
    }

    public void setDateNaiss(Instant dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaissance() {
        return this.lieuNaissance;
    }

    public Proprietaire lieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
        return this;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getNumCNI() {
        return this.numCNI;
    }

    public Proprietaire numCNI(String numCNI) {
        this.numCNI = numCNI;
        return this;
    }

    public void setNumCNI(String numCNI) {
        this.numCNI = numCNI;
    }

    public String getNinea() {
        return this.ninea;
    }

    public Proprietaire ninea(String ninea) {
        this.ninea = ninea;
        return this;
    }

    public void setNinea(String ninea) {
        this.ninea = ninea;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Proprietaire adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return this.email;
    }

    public Proprietaire email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Proprietaire telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone2() {
        return this.telephone2;
    }

    public Proprietaire telephone2(String telephone2) {
        this.telephone2 = telephone2;
        return this;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getTelephone3() {
        return this.telephone3;
    }

    public Proprietaire telephone3(String telephone3) {
        this.telephone3 = telephone3;
        return this;
    }

    public void setTelephone3(String telephone3) {
        this.telephone3 = telephone3;
    }

    public String getAquisition() {
        return this.aquisition;
    }

    public Proprietaire aquisition(String aquisition) {
        this.aquisition = aquisition;
        return this;
    }

    public void setAquisition(String aquisition) {
        this.aquisition = aquisition;
    }

    public String getStatutPersoneStructure() {
        return this.statutPersoneStructure;
    }

    public Proprietaire statutPersoneStructure(String statutPersoneStructure) {
        this.statutPersoneStructure = statutPersoneStructure;
        return this;
    }

    public void setStatutPersoneStructure(String statutPersoneStructure) {
        this.statutPersoneStructure = statutPersoneStructure;
    }

    public TypeStructure getTypeStructure() {
        return this.typeStructure;
    }

    public Instant getDateDelivrance() {
        return dateDelivrance;
    }

    public void setDateDelivrance(Instant dateDelivrance) {
        this.dateDelivrance = dateDelivrance;
    }

    public Proprietaire typeStructure(TypeStructure typeStructure) {
        this.typeStructure = typeStructure;
        return this;
    }

    public void setTypeStructure(TypeStructure typeStructure) {
        this.typeStructure = typeStructure;
    }

    public Integer getNombreHeritiers() {
        return this.nombreHeritiers;
    }

    public Proprietaire nombreHeritiers(Integer nombreHeritiers) {
        this.nombreHeritiers = nombreHeritiers;
        return this;
    }

    public void setNombreHeritiers(Integer nombreHeritiers) {
        this.nombreHeritiers = nombreHeritiers;
    }

    public String getServiceOcupant() {
        return this.serviceOcupant;
    }

    public Proprietaire serviceOcupant(String serviceOcupant) {
        this.serviceOcupant = serviceOcupant;
        return this;
    }

    public void setServiceOcupant(String serviceOcupant) {
        this.serviceOcupant = serviceOcupant;
    }

    public String getEtablssement() {
        return this.etablssement;
    }

    public Proprietaire etablssement(String etablssement) {
        this.etablssement = etablssement;
        return this;
    }

    public void setEtablssement(String etablssement) {
        this.etablssement = etablssement;
    }

    public Set<Dossier> getDossiers() {
        return this.dossiers;
    }

    public Proprietaire dossiers(Set<Dossier> dossiers) {
        this.setDossiers(dossiers);
        return this;
    }

    public Proprietaire addDossier(Dossier dossier) {
        this.dossiers.add(dossier);
        dossier.setProprietaire(this);
        return this;
    }

    public Proprietaire removeDossier(Dossier dossier) {
        this.dossiers.remove(dossier);
        dossier.setProprietaire(null);
        return this;
    }

    public void setDossiers(Set<Dossier> dossiers) {
        if (this.dossiers != null) {
            this.dossiers.forEach(i -> i.setProprietaire(null));
        }
        if (dossiers != null) {
            dossiers.forEach(i -> i.setProprietaire(this));
        }
        this.dossiers = dossiers;
    }

    public Set<Representant> getRepresentants() {
        return this.representants;
    }

    public Proprietaire representants(Set<Representant> representants) {
        this.setRepresentants(representants);
        return this;
    }

    public Proprietaire addRepresentant(Representant representant) {
        this.representants.add(representant);
        representant.setProprietaire(this);
        return this;
    }

    public Proprietaire removeRepresentant(Representant representant) {
        this.representants.remove(representant);
        representant.setProprietaire(null);
        return this;
    }

    public void setRepresentants(Set<Representant> representants) {
        if (this.representants != null) {
            this.representants.forEach(i -> i.setProprietaire(null));
        }
        if (representants != null) {
            representants.forEach(i -> i.setProprietaire(this));
        }
        this.representants = representants;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proprietaire)) {
            return false;
        }
        return id != null && id.equals(((Proprietaire) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Proprietaire{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", nom='" + getNom() + "'" +
            ", situation='" + getSituation() + "'" +
            ", gestionDelegue='" + getGestionDelegue() + "'" +
            ", raisonSocial='" + getRaisonSocial() + "'" +
            ", siegeSocial='" + getSiegeSocial() + "'" +
            ", personneMorale='" + getPersonneMorale() + "'" +
            ", dateNaiss='" + getDateNaiss() + "'" +
            ", lieuNaissance='" + getLieuNaissance() + "'" +
            ", numCNI='" + getNumCNI() + "'" +
            ", ninea='" + getNinea() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", email='" + getEmail() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", telephone2='" + getTelephone2() + "'" +
            ", telephone3='" + getTelephone3() + "'" +
            ", aquisition='" + getAquisition() + "'" +
            ", statutPersoneStructure='" + getStatutPersoneStructure() + "'" +
            ", typeStructure='" + getTypeStructure() + "'" +
            ", nombreHeritiers=" + getNombreHeritiers() +
            ", serviceOcupant='" + getServiceOcupant() + "'" +
            ", etablssement='" + getEtablssement() + "'" +
            ", dateDelivrance='" + getDateDelivrance() + "'" +
            "}";
    }
}
