package com.ibp.service.impl;

import com.ibp.service.EnvironnementService;
import com.ibp.domain.Environnement;
import com.ibp.repository.EnvironnementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Environnement}.
 */
@Service
@Transactional
public class EnvironnementServiceImpl implements EnvironnementService {

    private final Logger log = LoggerFactory.getLogger(EnvironnementServiceImpl.class);

    private final EnvironnementRepository environnementRepository;

    public EnvironnementServiceImpl(EnvironnementRepository environnementRepository) {
        this.environnementRepository = environnementRepository;
    }

    /**
     * Save a environnement.
     *
     * @param environnement the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Environnement save(Environnement environnement) {
        log.debug("Request to save Environnement : {}", environnement);
        return environnementRepository.save(environnement);
    }

    /**
     * Get all the environnements.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Environnement> findAll() {
        log.debug("Request to get all Environnements");
        return environnementRepository.findAll();
    }


    /**
     * Get one environnement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Environnement> findOne(Long id) {
        log.debug("Request to get Environnement : {}", id);
        return environnementRepository.findById(id);
    }

    /**
     * Delete the environnement by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Environnement : {}", id);
        environnementRepository.deleteById(id);
    }
}
