package com.pirtol.lab.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.pirtol.lab.domain.Refcadastrale} entity.
 */
public class RefcadastraleDTO implements Serializable {

    private Long id;

    private String codeSection;

    private String codeParcelle;

    private String nicad;

    private Double superfici;

    private String titreMere;

    private String titreCree;

    private String numeroRequisition;

    private Instant dateBornage;

    private Boolean titreFoncier;

    private Boolean titreNonImatricule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeSection() {
        return codeSection;
    }

    public void setCodeSection(String codeSection) {
        this.codeSection = codeSection;
    }

    public String getCodeParcelle() {
        return codeParcelle;
    }

    public void setCodeParcelle(String codeParcelle) {
        this.codeParcelle = codeParcelle;
    }

    public String getNicad() {
        return nicad;
    }

    public void setNicad(String nicad) {
        this.nicad = nicad;
    }

    public Double getSuperfici() {
        return superfici;
    }

    public void setSuperfici(Double superfici) {
        this.superfici = superfici;
    }

    public String getTitreMere() {
        return titreMere;
    }

    public void setTitreMere(String titreMere) {
        this.titreMere = titreMere;
    }

    public String getTitreCree() {
        return titreCree;
    }

    public void setTitreCree(String titreCree) {
        this.titreCree = titreCree;
    }

    public String getNumeroRequisition() {
        return numeroRequisition;
    }

    public void setNumeroRequisition(String numeroRequisition) {
        this.numeroRequisition = numeroRequisition;
    }

    public Instant getDateBornage() {
        return dateBornage;
    }

    public void setDateBornage(Instant dateBornage) {
        this.dateBornage = dateBornage;
    }

    public Boolean getTitreFoncier() {
        return titreFoncier;
    }

    public void setTitreFoncier(Boolean titreFoncier) {
        this.titreFoncier = titreFoncier;
    }

    public Boolean getTitreNonImatricule() {
        return titreNonImatricule;
    }

    public void setTitreNonImatricule(Boolean titreNonImatricule) {
        this.titreNonImatricule = titreNonImatricule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RefcadastraleDTO)) {
            return false;
        }

        RefcadastraleDTO refcadastraleDTO = (RefcadastraleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, refcadastraleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RefcadastraleDTO{" +
            "id=" + getId() +
            ", codeSection='" + getCodeSection() + "'" +
            ", codeParcelle='" + getCodeParcelle() + "'" +
            ", nicad='" + getNicad() + "'" +
            ", superfici=" + getSuperfici() +
            ", titreMere='" + getTitreMere() + "'" +
            ", titreCree='" + getTitreCree() + "'" +
            ", numeroRequisition='" + getNumeroRequisition() + "'" +
            ", dateBornage='" + getDateBornage() + "'" +
            ", titreFoncier='" + getTitreFoncier() + "'" +
            ", titreNonImatricule='" + getTitreNonImatricule() + "'" +
            "}";
    }
}
