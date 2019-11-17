package com.ibp.service;

import com.ibp.domain.Environnement;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Environnement}.
 */
public interface EnvironnementService {

    /**
     * Save a environnement.
     *
     * @param environnement the entity to save.
     * @return the persisted entity.
     */
    Environnement save(Environnement environnement);

    /**
     * Get all the environnements.
     *
     * @return the list of entities.
     */
    List<Environnement> findAll();


    /**
     * Get the "id" environnement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Environnement> findOne(Long id);

    /**
     * Delete the "id" environnement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
