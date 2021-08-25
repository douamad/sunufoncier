package com.pirtol.lab.service.dto;

import com.pirtol.lab.domain.enumeration.SituationProprietaire;
import com.pirtol.lab.domain.enumeration.TypeStructure;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.pirtol.lab.domain.Proprietaire} entity.
 */
public class ProprietaireDTO implements Serializable {

    private Long id;

    private String prenom;

    private String nom;

    private SituationProprietaire situation;

    private Boolean gestionDelegue;

    private String raisonSocial;

    private String siegeSocial;

    private Boolean personneMorale;

    private Instant dateNaiss;

    private String lieuNaissance;

    private String numCNI;

    private String ninea;

    private String adresse;

    private String email;

    private String telephone;

    private String telephone2;

    private String telephone3;

    private String aquisition;

    private String statutPersoneStructure;

    private TypeStructure typeStructure;

    private Integer nombreHeritiers;

    private String serviceOcupant;

    private String etablssement;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public SituationProprietaire getSituation() {
        return situation;
    }

    public void setSituation(SituationProprietaire situation) {
        this.situation = situation;
    }

    public Boolean getGestionDelegue() {
        return gestionDelegue;
    }

    public void setGestionDelegue(Boolean gestionDelegue) {
        this.gestionDelegue = gestionDelegue;
    }

    public String getRaisonSocial() {
        return raisonSocial;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public String getSiegeSocial() {
        return siegeSocial;
    }

    public void setSiegeSocial(String siegeSocial) {
        this.siegeSocial = siegeSocial;
    }

    public Boolean getPersonneMorale() {
        return personneMorale;
    }

    public void setPersonneMorale(Boolean personneMorale) {
        this.personneMorale = personneMorale;
    }

    public Instant getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(Instant dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getNumCNI() {
        return numCNI;
    }

    public void setNumCNI(String numCNI) {
        this.numCNI = numCNI;
    }

    public String getNinea() {
        return ninea;
    }

    public void setNinea(String ninea) {
        this.ninea = ninea;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getTelephone3() {
        return telephone3;
    }

    public void setTelephone3(String telephone3) {
        this.telephone3 = telephone3;
    }

    public String getAquisition() {
        return aquisition;
    }

    public void setAquisition(String aquisition) {
        this.aquisition = aquisition;
    }

    public String getStatutPersoneStructure() {
        return statutPersoneStructure;
    }

    public void setStatutPersoneStructure(String statutPersoneStructure) {
        this.statutPersoneStructure = statutPersoneStructure;
    }

    public TypeStructure getTypeStructure() {
        return typeStructure;
    }

    public void setTypeStructure(TypeStructure typeStructure) {
        this.typeStructure = typeStructure;
    }

    public Integer getNombreHeritiers() {
        return nombreHeritiers;
    }

    public void setNombreHeritiers(Integer nombreHeritiers) {
        this.nombreHeritiers = nombreHeritiers;
    }

    public String getServiceOcupant() {
        return serviceOcupant;
    }

    public void setServiceOcupant(String serviceOcupant) {
        this.serviceOcupant = serviceOcupant;
    }

    public String getEtablssement() {
        return etablssement;
    }

    public void setEtablssement(String etablssement) {
        this.etablssement = etablssement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProprietaireDTO)) {
            return false;
        }

        ProprietaireDTO proprietaireDTO = (ProprietaireDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, proprietaireDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProprietaireDTO{" +
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
            "}";
    }
}
