package com.ibp.service;

import com.ibp.domain.PF;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PF}.
 */
public interface PFService {

    /**
     * Save a pF.
     *
     * @param pF the entity to save.
     * @return the persisted entity.
     */
    PF save(PF pF);

    /**
     * Get all the pFS.
     *
     * @return the list of entities.
     */
    List<PF> findAll();


    /**
     * Get the "id" pF.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PF> findOne(Long id);

    /**
     * Delete the "id" pF.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
