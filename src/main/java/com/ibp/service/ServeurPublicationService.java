package com.ibp.service;

import com.ibp.domain.ServeurPublication;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ServeurPublication}.
 */
public interface ServeurPublicationService {

    /**
     * Save a serveurPublication.
     *
     * @param serveurPublication the entity to save.
     * @return the persisted entity.
     */
    ServeurPublication save(ServeurPublication serveurPublication);

    /**
     * Get all the serveurPublications.
     *
     * @return the list of entities.
     */
    List<ServeurPublication> findAll();


    /**
     * Get the "id" serveurPublication.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServeurPublication> findOne(Long id);

    /**
     * Delete the "id" serveurPublication.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
