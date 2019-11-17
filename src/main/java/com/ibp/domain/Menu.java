package com.ibp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @NotNull
    @ApiModelProperty(value = "The firstname attribute.", required = true)
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "infobulle")
    private String infobulle;

    @Column(name = "commentaire")
    private String commentaire;

    @OneToMany(mappedBy = "menu")
    private Set<Entree> entrees = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("menus")
    private Menu manager;

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

    public Menu nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getInfobulle() {
        return infobulle;
    }

    public Menu infobulle(String infobulle) {
        this.infobulle = infobulle;
        return this;
    }

    public void setInfobulle(String infobulle) {
        this.infobulle = infobulle;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Menu commentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Set<Entree> getEntrees() {
        return entrees;
    }

    public Menu entrees(Set<Entree> entrees) {
        this.entrees = entrees;
        return this;
    }

    public Menu addEntree(Entree entree) {
        this.entrees.add(entree);
        entree.setMenu(this);
        return this;
    }

    public Menu removeEntree(Entree entree) {
        this.entrees.remove(entree);
        entree.setMenu(null);
        return this;
    }

    public void setEntrees(Set<Entree> entrees) {
        this.entrees = entrees;
    }

    public Menu getManager() {
        return manager;
    }

    public Menu manager(Menu menu) {
        this.manager = menu;
        return this;
    }

    public void setManager(Menu menu) {
        this.manager = menu;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        return id != null && id.equals(((Menu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", infobulle='" + getInfobulle() + "'" +
            ", commentaire='" + getCommentaire() + "'" +
            "}";
    }
}
