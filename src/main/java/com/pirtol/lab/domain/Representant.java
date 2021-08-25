package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pirtol.lab.domain.enumeration.TypeStructure;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Representant.
 */
@Entity
@Table(name = "representant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Representant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "lien_proprietaire")
    private String lienProprietaire;

    @Column(name = "nom")
    private String nom;

    @Column(name = "actif")
    private Boolean actif;

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

    @Column(name = "statut_persone_structure")
    private String statutPersoneStructure;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_structure")
    private TypeStructure typeStructure;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dossiers", "representants" }, allowSetters = true)
    private Proprietaire proprietaire;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Representant id(Long id) {
        this.id = id;
        return this;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Representant prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLienProprietaire() {
        return this.lienProprietaire;
    }

    public Representant lienProprietaire(String lienProprietaire) {
        this.lienProprietaire = lienProprietaire;
        return this;
    }

    public void setLienProprietaire(String lienProprietaire) {
        this.lienProprietaire = lienProprietaire;
    }

    public String getNom() {
        return this.nom;
    }

    public Representant nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getActif() {
        return this.actif;
    }

    public Representant actif(Boolean actif) {
        this.actif = actif;
        return this;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    public String getRaisonSocial() {
        return this.raisonSocial;
    }

    public Representant raisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
        return this;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public String getSiegeSocial() {
        return this.siegeSocial;
    }

    public Representant siegeSocial(String siegeSocial) {
        this.siegeSocial = siegeSocial;
        return this;
    }

    public void setSiegeSocial(String siegeSocial) {
        this.siegeSocial = siegeSocial;
    }

    public Boolean getPersonneMorale() {
        return this.personneMorale;
    }

    public Representant personneMorale(Boolean personneMorale) {
        this.personneMorale = personneMorale;
        return this;
    }

    public void setPersonneMorale(Boolean personneMorale) {
        this.personneMorale = personneMorale;
    }

    public Instant getDateNaiss() {
        return this.dateNaiss;
    }

    public Representant dateNaiss(Instant dateNaiss) {
        this.dateNaiss = dateNaiss;
        return this;
    }

    public void setDateNaiss(Instant dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaissance() {
        return this.lieuNaissance;
    }

    public Representant lieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
        return this;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getNumCNI() {
        return this.numCNI;
    }

    public Representant numCNI(String numCNI) {
        this.numCNI = numCNI;
        return this;
    }

    public void setNumCNI(String numCNI) {
        this.numCNI = numCNI;
    }

    public String getNinea() {
        return this.ninea;
    }

    public Representant ninea(String ninea) {
        this.ninea = ninea;
        return this;
    }

    public void setNinea(String ninea) {
        this.ninea = ninea;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Representant adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return this.email;
    }

    public Representant email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Representant telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone2() {
        return this.telephone2;
    }

    public Representant telephone2(String telephone2) {
        this.telephone2 = telephone2;
        return this;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getTelephone3() {
        return this.telephone3;
    }

    public Representant telephone3(String telephone3) {
        this.telephone3 = telephone3;
        return this;
    }

    public void setTelephone3(String telephone3) {
        this.telephone3 = telephone3;
    }

    public String getStatutPersoneStructure() {
        return this.statutPersoneStructure;
    }

    public Representant statutPersoneStructure(String statutPersoneStructure) {
        this.statutPersoneStructure = statutPersoneStructure;
        return this;
    }

    public void setStatutPersoneStructure(String statutPersoneStructure) {
        this.statutPersoneStructure = statutPersoneStructure;
    }

    public TypeStructure getTypeStructure() {
        return this.typeStructure;
    }

    public Representant typeStructure(TypeStructure typeStructure) {
        this.typeStructure = typeStructure;
        return this;
    }

    public void setTypeStructure(TypeStructure typeStructure) {
        this.typeStructure = typeStructure;
    }

    public Proprietaire getProprietaire() {
        return this.proprietaire;
    }

    public Representant proprietaire(Proprietaire proprietaire) {
        this.setProprietaire(proprietaire);
        return this;
    }

    public void setProprietaire(Proprietaire proprietaire) {
        this.proprietaire = proprietaire;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Representant)) {
            return false;
        }
        return id != null && id.equals(((Representant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Representant{" +
            "id=" + getId() +
            ", prenom='" + getPrenom() + "'" +
            ", lienProprietaire='" + getLienProprietaire() + "'" +
            ", nom='" + getNom() + "'" +
            ", actif='" + getActif() + "'" +
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
            ", statutPersoneStructure='" + getStatutPersoneStructure() + "'" +
            ", typeStructure='" + getTypeStructure() + "'" +
            "}";
    }
}
