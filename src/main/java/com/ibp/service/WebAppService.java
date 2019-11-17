package com.ibp.service;

import com.ibp.domain.WebApp;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link WebApp}.
 */
public interface WebAppService {

    /**
     * Save a webApp.
     *
     * @param webApp the entity to save.
     * @return the persisted entity.
     */
    WebApp save(WebApp webApp);

    /**
     * Get all the webApps.
     *
     * @return the list of entities.
     */
    List<WebApp> findAll();


    /**
     * Get the "id" webApp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WebApp> findOne(Long id);

    /**
     * Delete the "id" webApp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
