package com.ibp.service;

import com.ibp.domain.RBExe;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RBExe}.
 */
public interface RBExeService {

    /**
     * Save a rBExe.
     *
     * @param rBExe the entity to save.
     * @return the persisted entity.
     */
    RBExe save(RBExe rBExe);

    /**
     * Get all the rBExes.
     *
     * @return the list of entities.
     */
    List<RBExe> findAll();


    /**
     * Get the "id" rBExe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RBExe> findOne(Long id);

    /**
     * Delete the "id" rBExe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
