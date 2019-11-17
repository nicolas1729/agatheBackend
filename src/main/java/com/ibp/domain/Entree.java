package com.ibp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Entree.
 */
@Entity
@Table(name = "entree")
public class Entree implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "infobulle")
    private String infobulle;

    @Column(name = "commentaire")
    private String commentaire;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("entrees")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("entrees")
    private Menu menu;

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

    public Entree nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getInfobulle() {
        return infobulle;
    }

    public Entree infobulle(String infobulle) {
        this.infobulle = infobulle;
        return this;
    }

    public void setInfobulle(String infobulle) {
        this.infobulle = infobulle;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Entree commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getDescription() {
        return description;
    }

    public Entree description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task getTask() {
        return task;
    }

    public Entree task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Menu getMenu() {
        return menu;
    }

    public Entree menu(Menu menu) {
        this.menu = menu;
        return this;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entree)) {
            return false;
        }
        return id != null && id.equals(((Entree) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Entree{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", infobulle='" + getInfobulle() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
