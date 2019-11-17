package com.ibp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Version.
 */
@Entity
@Table(name = "version")
public class Version implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "commentaire")
    private String commentaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("versions")
    private VersionCommunautaire versionCommunautaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("versions")
    private VersionPrivative versionPrivative;

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

    public Version nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Version commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public VersionCommunautaire getVersionCommunautaire() {
        return versionCommunautaire;
    }

    public Version versionCommunautaire(VersionCommunautaire versionCommunautaire) {
        this.versionCommunautaire = versionCommunautaire;
        return this;
    }

    public void setVersionCommunautaire(VersionCommunautaire versionCommunautaire) {
        this.versionCommunautaire = versionCommunautaire;
    }

    public VersionPrivative getVersionPrivative() {
        return versionPrivative;
    }

    public Version versionPrivative(VersionPrivative versionPrivative) {
        this.versionPrivative = versionPrivative;
        return this;
    }

    public void setVersionPrivative(VersionPrivative versionPrivative) {
        this.versionPrivative = versionPrivative;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Version)) {
            return false;
        }
        return id != null && id.equals(((Version) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Version{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
