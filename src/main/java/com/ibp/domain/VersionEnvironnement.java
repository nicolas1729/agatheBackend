package com.ibp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A VersionEnvironnement.
 */
@Entity
@Table(name = "version_environnement")
public class VersionEnvironnement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "date_publication")
    private Instant datePublication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("versionEnvironnements")
    private Version version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("versionEnvironnements")
    private Environnement environnement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public VersionEnvironnement nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Instant getDatePublication() {
        return datePublication;
    }

    public VersionEnvironnement datePublication(Instant datePublication) {
        this.datePublication = datePublication;
        return this;
    }

    public void setDatePublication(Instant datePublication) {
        this.datePublication = datePublication;
    }

    public Version getVersion() {
        return version;
    }

    public VersionEnvironnement version(Version version) {
        this.version = version;
        return this;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Environnement getEnvironnement() {
        return environnement;
    }

    public VersionEnvironnement environnement(Environnement environnement) {
        this.environnement = environnement;
        return this;
    }

    public void setEnvironnement(Environnement environnement) {
        this.environnement = environnement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VersionEnvironnement)) {
            return false;
        }
        return id != null && id.equals(((VersionEnvironnement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "VersionEnvironnement{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", datePublication='" + getDatePublication() + "'" +
            "}";
    }
}
