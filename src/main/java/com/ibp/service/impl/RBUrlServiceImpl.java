package com.ibp.service.impl;

import com.ibp.service.RBUrlService;
import com.ibp.domain.RBUrl;
import com.ibp.repository.RBUrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link RBUrl}.
 */
@Service
@Transactional
public class RBUrlServiceImpl implements RBUrlService {

    private final Logger log = LoggerFactory.getLogger(RBUrlServiceImpl.class);

    private final RBUrlRepository rBUrlRepository;

    public RBUrlServiceImpl(RBUrlRepository rBUrlRepository) {
        this.rBUrlRepository = rBUrlRepository;
    }

    /**
     * Save a rBUrl.
     *
     * @param rBUrl the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RBUrl save(RBUrl rBUrl) {
        log.debug("Request to save RBUrl : {}", rBUrl);
        return rBUrlRepository.save(rBUrl);
    }

    /**
     * Get all the rBUrls.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RBUrl> findAll() {
        log.debug("Request to get all RBUrls");
        return rBUrlRepository.findAll();
    }


    /**
     * Get one rBUrl by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RBUrl> findOne(Long id) {
        log.debug("Request to get RBUrl : {}", id);
        return rBUrlRepository.findById(id);
    }

    /**
     * Delete the rBUrl by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RBUrl : {}", id);
        rBUrlRepository.deleteById(id);
    }
}
