package com.ibp.service;

import com.ibp.domain.VersionEnvironnement;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link VersionEnvironnement}.
 */
public interface VersionEnvironnementService {

    /**
     * Save a versionEnvironnement.
     *
     * @param versionEnvironnement the entity to save.
     * @return the persisted entity.
     */
    VersionEnvironnement save(VersionEnvironnement versionEnvironnement);

    /**
     * Get all the versionEnvironnements.
     *
     * @return the list of entities.
     */
    List<VersionEnvironnement> findAll();


    /**
     * Get the "id" versionEnvironnement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VersionEnvironnement> findOne(Long id);

    /**
     * Delete the "id" versionEnvironnement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
