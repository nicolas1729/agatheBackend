package com.ibp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ServeurPublication.
 */
@Entity
@Table(name = "serveur_publication")
public class ServeurPublication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "path", nullable = false)
    private String path;

    @NotNull
    @Column(name = "jboss", nullable = false)
    private Boolean jboss;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("serveurPublications")
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

    public ServeurPublication nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPath() {
        return path;
    }

    public ServeurPublication path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean isJboss() {
        return jboss;
    }

    public ServeurPublication jboss(Boolean jboss) {
        this.jboss = jboss;
        return this;
    }

    public void setJboss(Boolean jboss) {
        this.jboss = jboss;
    }

    public Environnement getEnvironnement() {
        return environnement;
    }

    public ServeurPublication environnement(Environnement environnement) {
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
        if (!(o instanceof ServeurPublication)) {
            return false;
        }
        return id != null && id.equals(((ServeurPublication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServeurPublication{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", path='" + getPath() + "'" +
            ", jboss='" + isJboss() + "'" +
            "}";
    }
}
