package com.pirtol.lab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Refcadastrale.
 */
@Entity
@Table(name = "refcadastrale")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Refcadastrale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code_section")
    private String codeSection;

    @Column(name = "code_parcelle")
    private String codeParcelle;

    @Column(name = "nicad")
    private String nicad;

    @Column(name = "superfici")
    private Double superfici;

    @Column(name = "titre_mere")
    private String titreMere;

    @Column(name = "titre_cree")
    private String titreCree;

    @Column(name = "numero_requisition")
    private String numeroRequisition;

    @Column(name = "date_bornage")
    private Instant dateBornage;

    @OneToMany(mappedBy = "refcadastrale")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Refcadastrale id(Long id) {
        this.id = id;
        return this;
    }

    public String getCodeSection() {
        return this.codeSection;
    }

    public Refcadastrale codeSection(String codeSection) {
        this.codeSection = codeSection;
        return this;
    }

    public void setCodeSection(String codeSection) {
        this.codeSection = codeSection;
    }

    public String getCodeParcelle() {
        return this.codeParcelle;
    }

    public Refcadastrale codeParcelle(String codeParcelle) {
        this.codeParcelle = codeParcelle;
        return this;
    }

    public void setCodeParcelle(String codeParcelle) {
        this.codeParcelle = codeParcelle;
    }

    public String getNicad() {
        return this.nicad;
    }

    public Refcadastrale nicad(String nicad) {
        this.nicad = nicad;
        return this;
    }

    public void setNicad(String nicad) {
        this.nicad = nicad;
    }

    public Double getSuperfici() {
        return this.superfici;
    }

    public Refcadastrale superfici(Double superfici) {
        this.superfici = superfici;
        return this;
    }

    public void setSuperfici(Double superfici) {
        this.superfici = superfici;
    }

    public String getTitreMere() {
        return this.titreMere;
    }

    public Refcadastrale titreMere(String titreMere) {
        this.titreMere = titreMere;
        return this;
    }

    public void setTitreMere(String titreMere) {
        this.titreMere = titreMere;
    }

    public String getTitreCree() {
        return this.titreCree;
    }

    public Refcadastrale titreCree(String titreCree) {
        this.titreCree = titreCree;
        return this;
    }

    public void setTitreCree(String titreCree) {
        this.titreCree = titreCree;
    }

    public String getNumeroRequisition() {
        return this.numeroRequisition;
    }

    public Refcadastrale numeroRequisition(String numeroRequisition) {
        this.numeroRequisition = numeroRequisition;
        return this;
    }

    public void setNumeroRequisition(String numeroRequisition) {
        this.numeroRequisition = numeroRequisition;
    }

    public Instant getDateBornage() {
        return this.dateBornage;
    }

    public Refcadastrale dateBornage(Instant dateBornage) {
        this.dateBornage = dateBornage;
        return this;
    }

    public void setDateBornage(Instant dateBornage) {
        this.dateBornage = dateBornage;
    }

    public Set<Dossier> getDossiers() {
        return this.dossiers;
    }

    public Refcadastrale dossiers(Set<Dossier> dossiers) {
        this.setDossiers(dossiers);
        return this;
    }

    public Refcadastrale addDossier(Dossier dossier) {
        this.dossiers.add(dossier);
        dossier.setRefcadastrale(this);
        return this;
    }

    public Refcadastrale removeDossier(Dossier dossier) {
        this.dossiers.remove(dossier);
        dossier.setRefcadastrale(null);
        return this;
    }

    public void setDossiers(Set<Dossier> dossiers) {
        if (this.dossiers != null) {
            this.dossiers.forEach(i -> i.setRefcadastrale(null));
        }
        if (dossiers != null) {
            dossiers.forEach(i -> i.setRefcadastrale(this));
        }
        this.dossiers = dossiers;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Refcadastrale)) {
            return false;
        }
        return id != null && id.equals(((Refcadastrale) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Refcadastrale{" +
            "id=" + getId() +
            ", codeSection='" + getCodeSection() + "'" +
            ", codeParcelle='" + getCodeParcelle() + "'" +
            ", nicad='" + getNicad() + "'" +
            ", superfici=" + getSuperfici() +
            ", titreMere='" + getTitreMere() + "'" +
            ", titreCree='" + getTitreCree() + "'" +
            ", numeroRequisition='" + getNumeroRequisition() + "'" +
            ", dateBornage='" + getDateBornage() + "'" +
            "}";
    }
}
