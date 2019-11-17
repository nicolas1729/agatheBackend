package com.ibp.service;

import com.ibp.domain.VersionPrivative;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link VersionPrivative}.
 */
public interface VersionPrivativeService {

    /**
     * Save a versionPrivative.
     *
     * @param versionPrivative the entity to save.
     * @return the persisted entity.
     */
    VersionPrivative save(VersionPrivative versionPrivative);

    /**
     * Get all the versionPrivatives.
     *
     * @return the list of entities.
     */
    List<VersionPrivative> findAll();


    /**
     * Get the "id" versionPrivative.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VersionPrivative> findOne(Long id);

    /**
     * Delete the "id" versionPrivative.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
