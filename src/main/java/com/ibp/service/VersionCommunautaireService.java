package com.ibp.service;

import com.ibp.domain.VersionCommunautaire;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link VersionCommunautaire}.
 */
public interface VersionCommunautaireService {

    /**
     * Save a versionCommunautaire.
     *
     * @param versionCommunautaire the entity to save.
     * @return the persisted entity.
     */
    VersionCommunautaire save(VersionCommunautaire versionCommunautaire);

    /**
     * Get all the versionCommunautaires.
     *
     * @return the list of entities.
     */
    List<VersionCommunautaire> findAll();


    /**
     * Get the "id" versionCommunautaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VersionCommunautaire> findOne(Long id);

    /**
     * Delete the "id" versionCommunautaire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
