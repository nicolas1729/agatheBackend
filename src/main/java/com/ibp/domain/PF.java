package com.ibp.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PF.
 */
@Entity
@Table(name = "pf")
public class PF implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "id_task", nullable = false)
    private String idTask;

    @NotNull
    @Column(name = "id_process", nullable = false)
    private String idProcess;

    @OneToOne(fetch = FetchType.LAZY)

    @JoinColumn(unique = true)
    private Task task;

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

    public PF nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public PF description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdTask() {
        return idTask;
    }

    public PF idTask(String idTask) {
        this.idTask = idTask;
        return this;
    }

    public void setIdTask(String idTask) {
        this.idTask = idTask;
    }

    public String getIdProcess() {
        return idProcess;
    }

    public PF idProcess(String idProcess) {
        this.idProcess = idProcess;
        return this;
    }

    public void setIdProcess(String idProcess) {
        this.idProcess = idProcess;
    }

    public Task getTask() {
        return task;
    }

    public PF task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PF)) {
            return false;
        }
        return id != null && id.equals(((PF) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PF{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", idTask='" + getIdTask() + "'" +
            ", idProcess='" + getIdProcess() + "'" +
            "}";
    }
}
