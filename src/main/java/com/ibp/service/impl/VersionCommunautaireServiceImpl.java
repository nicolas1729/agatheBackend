package com.ibp.service.impl;

import com.ibp.service.VersionCommunautaireService;
import com.ibp.domain.VersionCommunautaire;
import com.ibp.repository.VersionCommunautaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link VersionCommunautaire}.
 */
@Service
@Transactional
public class VersionCommunautaireServiceImpl implements VersionCommunautaireService {

    private final Logger log = LoggerFactory.getLogger(VersionCommunautaireServiceImpl.class);

    private final VersionCommunautaireRepository versionCommunautaireRepository;

    public VersionCommunautaireServiceImpl(VersionCommunautaireRepository versionCommunautaireRepository) {
        this.versionCommunautaireRepository = versionCommunautaireRepository;
    }

    /**
     * Save a versionCommunautaire.
     *
     * @param versionCommunautaire the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VersionCommunautaire save(VersionCommunautaire versionCommunautaire) {
        log.debug("Request to save VersionCommunautaire : {}", versionCommunautaire);
        return versionCommunautaireRepository.save(versionCommunautaire);
    }

    /**
     * Get all the versionCommunautaires.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VersionCommunautaire> findAll() {
        log.debug("Request to get all VersionCommunautaires");
        return versionCommunautaireRepository.findAll();
    }


    /**
     * Get one versionCommunautaire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VersionCommunautaire> findOne(Long id) {
        log.debug("Request to get VersionCommunautaire : {}", id);
        return versionCommunautaireRepository.findById(id);
    }

    /**
     * Delete the versionCommunautaire by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VersionCommunautaire : {}", id);
        versionCommunautaireRepository.deleteById(id);
    }
}
