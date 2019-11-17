package com.ibp.service.impl;

import com.ibp.service.PFService;
import com.ibp.domain.PF;
import com.ibp.repository.PFRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PF}.
 */
@Service
@Transactional
public class PFServiceImpl implements PFService {

    private final Logger log = LoggerFactory.getLogger(PFServiceImpl.class);

    private final PFRepository pFRepository;

    public PFServiceImpl(PFRepository pFRepository) {
        this.pFRepository = pFRepository;
    }

    /**
     * Save a pF.
     *
     * @param pF the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PF save(PF pF) {
        log.debug("Request to save PF : {}", pF);
        return pFRepository.save(pF);
    }

    /**
     * Get all the pFS.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PF> findAll() {
        log.debug("Request to get all PFS");
        return pFRepository.findAll();
    }


    /**
     * Get one pF by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PF> findOne(Long id) {
        log.debug("Request to get PF : {}", id);
        return pFRepository.findById(id);
    }

    /**
     * Delete the pF by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PF : {}", id);
        pFRepository.deleteById(id);
    }
}
