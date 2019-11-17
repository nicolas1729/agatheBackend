package com.ibp.service.impl;

import com.ibp.service.ServeurPublicationService;
import com.ibp.domain.ServeurPublication;
import com.ibp.repository.ServeurPublicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ServeurPublication}.
 */
@Service
@Transactional
public class ServeurPublicationServiceImpl implements ServeurPublicationService {

    private final Logger log = LoggerFactory.getLogger(ServeurPublicationServiceImpl.class);

    private final ServeurPublicationRepository serveurPublicationRepository;

    public ServeurPublicationServiceImpl(ServeurPublicationRepository serveurPublicationRepository) {
        this.serveurPublicationRepository = serveurPublicationRepository;
    }

    /**
     * Save a serveurPublication.
     *
     * @param serveurPublication the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServeurPublication save(ServeurPublication serveurPublication) {
        log.debug("Request to save ServeurPublication : {}", serveurPublication);
        return serveurPublicationRepository.save(serveurPublication);
    }

    /**
     * Get all the serveurPublications.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ServeurPublication> findAll() {
        log.debug("Request to get all ServeurPublications");
        return serveurPublicationRepository.findAll();
    }


    /**
     * Get one serveurPublication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServeurPublication> findOne(Long id) {
        log.debug("Request to get ServeurPublication : {}", id);
        return serveurPublicationRepository.findById(id);
    }

    /**
     * Delete the serveurPublication by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServeurPublication : {}", id);
        serveurPublicationRepository.deleteById(id);
    }
}
