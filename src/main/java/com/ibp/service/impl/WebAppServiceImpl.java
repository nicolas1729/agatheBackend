package com.ibp.service.impl;

import com.ibp.service.WebAppService;
import com.ibp.domain.WebApp;
import com.ibp.repository.WebAppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link WebApp}.
 */
@Service
@Transactional
public class WebAppServiceImpl implements WebAppService {

    private final Logger log = LoggerFactory.getLogger(WebAppServiceImpl.class);

    private final WebAppRepository webAppRepository;

    public WebAppServiceImpl(WebAppRepository webAppRepository) {
        this.webAppRepository = webAppRepository;
    }

    /**
     * Save a webApp.
     *
     * @param webApp the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WebApp save(WebApp webApp) {
        log.debug("Request to save WebApp : {}", webApp);
        return webAppRepository.save(webApp);
    }

    /**
     * Get all the webApps.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<WebApp> findAll() {
        log.debug("Request to get all WebApps");
        return webAppRepository.findAll();
    }


    /**
     * Get one webApp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WebApp> findOne(Long id) {
        log.debug("Request to get WebApp : {}", id);
        return webAppRepository.findById(id);
    }

    /**
     * Delete the webApp by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebApp : {}", id);
        webAppRepository.deleteById(id);
    }
}
