package com.ibp.service;

import com.ibp.domain.RBUrl;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RBUrl}.
 */
public interface RBUrlService {

    /**
     * Save a rBUrl.
     *
     * @param rBUrl the entity to save.
     * @return the persisted entity.
     */
    RBUrl save(RBUrl rBUrl);

    /**
     * Get all the rBUrls.
     *
     * @return the list of entities.
     */
    List<RBUrl> findAll();


    /**
     * Get the "id" rBUrl.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RBUrl> findOne(Long id);

    /**
     * Delete the "id" rBUrl.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
