package com.ibp.service.impl;

import com.ibp.service.RBExeService;
import com.ibp.domain.RBExe;
import com.ibp.repository.RBExeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link RBExe}.
 */
@Service
@Transactional
public class RBExeServiceImpl implements RBExeService {

    private final Logger log = LoggerFactory.getLogger(RBExeServiceImpl.class);

    private final RBExeRepository rBExeRepository;

    public RBExeServiceImpl(RBExeRepository rBExeRepository) {
        this.rBExeRepository = rBExeRepository;
    }

    /**
     * Save a rBExe.
     *
     * @param rBExe the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RBExe save(RBExe rBExe) {
        log.debug("Request to save RBExe : {}", rBExe);
        return rBExeRepository.save(rBExe);
    }

    /**
     * Get all the rBExes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RBExe> findAll() {
        log.debug("Request to get all RBExes");
        return rBExeRepository.findAll();
    }


    /**
     * Get one rBExe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RBExe> findOne(Long id) {
        log.debug("Request to get RBExe : {}", id);
        return rBExeRepository.findById(id);
    }

    /**
     * Delete the rBExe by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RBExe : {}", id);
        rBExeRepository.deleteById(id);
    }
}
