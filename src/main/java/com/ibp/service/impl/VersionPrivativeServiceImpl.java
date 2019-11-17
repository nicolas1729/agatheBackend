package com.ibp.service.impl;

import com.ibp.service.VersionPrivativeService;
import com.ibp.domain.VersionPrivative;
import com.ibp.repository.VersionPrivativeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link VersionPrivative}.
 */
@Service
@Transactional
public class VersionPrivativeServiceImpl implements VersionPrivativeService {

    private final Logger log = LoggerFactory.getLogger(VersionPrivativeServiceImpl.class);

    private final VersionPrivativeRepository versionPrivativeRepository;

    public VersionPrivativeServiceImpl(VersionPrivativeRepository versionPrivativeRepository) {
        this.versionPrivativeRepository = versionPrivativeRepository;
    }

    /**
     * Save a versionPrivative.
     *
     * @param versionPrivative the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VersionPrivative save(VersionPrivative versionPrivative) {
        log.debug("Request to save VersionPrivative : {}", versionPrivative);
        return versionPrivativeRepository.save(versionPrivative);
    }

    /**
     * Get all the versionPrivatives.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VersionPrivative> findAll() {
        log.debug("Request to get all VersionPrivatives");
        return versionPrivativeRepository.findAll();
    }


    /**
     * Get one versionPrivative by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VersionPrivative> findOne(Long id) {
        log.debug("Request to get VersionPrivative : {}", id);
        return versionPrivativeRepository.findById(id);
    }

    /**
     * Delete the versionPrivative by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VersionPrivative : {}", id);
        versionPrivativeRepository.deleteById(id);
    }
}
