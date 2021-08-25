package com.pirtol.lab.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.pirtol.lab.domain.Dossier} entity.
 */
public class DossierDTO implements Serializable {

    private Long id;

    private String numero;

    private Double valeurBatie;

    private Double valeurVenale;

    private Double valeurLocativ;

    private String activite;

    private LotissementDTO dossier;

    private UsageDossierDTO usageDossier;

    private ProprietaireDTO proprietaire;

    private RefParcelaireDTO refParcelaire;

    private RefcadastraleDTO refcadastrale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Double getValeurBatie() {
        return valeurBatie;
    }

    public void setValeurBatie(Double valeurBatie) {
        this.valeurBatie = valeurBatie;
    }

    public Double getValeurVenale() {
        return valeurVenale;
    }

    public void setValeurVenale(Double valeurVenale) {
        this.valeurVenale = valeurVenale;
    }

    public Double getValeurLocativ() {
        return valeurLocativ;
    }

    public void setValeurLocativ(Double valeurLocativ) {
        this.valeurLocativ = valeurLocativ;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }

    public LotissementDTO getDossier() {
        return dossier;
    }

    public void setDossier(LotissementDTO dossier) {
        this.dossier = dossier;
    }

    public UsageDossierDTO getUsageDossier() {
        return usageDossier;
    }

    public void setUsageDossier(UsageDossierDTO usageDossier) {
        this.usageDossier = usageDossier;
    }

    public ProprietaireDTO getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(ProprietaireDTO proprietaire) {
        this.proprietaire = proprietaire;
    }

    public RefParcelaireDTO getRefParcelaire() {
        return refParcelaire;
    }

    public void setRefParcelaire(RefParcelaireDTO refParcelaire) {
        this.refParcelaire = refParcelaire;
    }

    public RefcadastraleDTO getRefcadastrale() {
        return refcadastrale;
    }

    public void setRefcadastrale(RefcadastraleDTO refcadastrale) {
        this.refcadastrale = refcadastrale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DossierDTO)) {
            return false;
        }

        DossierDTO dossierDTO = (DossierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dossierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DossierDTO{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", valeurBatie=" + getValeurBatie() +
            ", valeurVenale=" + getValeurVenale() +
            ", valeurLocativ=" + getValeurLocativ() +
            ", activite='" + getActivite() + "'" +
            ", dossier=" + getDossier() +
            ", usageDossier=" + getUsageDossier() +
            ", proprietaire=" + getProprietaire() +
            ", refParcelaire=" + getRefParcelaire() +
            ", refcadastrale=" + getRefcadastrale() +
            "}";
    }
}
