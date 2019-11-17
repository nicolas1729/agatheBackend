package com.ibp.service.impl;

import com.ibp.service.VersionEnvironnementService;
import com.ibp.domain.VersionEnvironnement;
import com.ibp.repository.VersionEnvironnementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link VersionEnvironnement}.
 */
@Service
@Transactional
public class VersionEnvironnementServiceImpl implements VersionEnvironnementService {

    private final Logger log = LoggerFactory.getLogger(VersionEnvironnementServiceImpl.class);

    private final VersionEnvironnementRepository versionEnvironnementRepository;

    public VersionEnvironnementServiceImpl(VersionEnvironnementRepository versionEnvironnementRepository) {
        this.versionEnvironnementRepository = versionEnvironnementRepository;
    }

    /**
     * Save a versionEnvironnement.
     *
     * @param versionEnvironnement the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VersionEnvironnement save(VersionEnvironnement versionEnvironnement) {
        log.debug("Request to save VersionEnvironnement : {}", versionEnvironnement);
        return versionEnvironnementRepository.save(versionEnvironnement);
    }

    /**
     * Get all the versionEnvironnements.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VersionEnvironnement> findAll() {
        log.debug("Request to get all VersionEnvironnements");
        return versionEnvironnementRepository.findAll();
    }


    /**
     * Get one versionEnvironnement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VersionEnvironnement> findOne(Long id) {
        log.debug("Request to get VersionEnvironnement : {}", id);
        return versionEnvironnementRepository.findById(id);
    }

    /**
     * Delete the versionEnvironnement by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VersionEnvironnement : {}", id);
        versionEnvironnementRepository.deleteById(id);
    }
}
