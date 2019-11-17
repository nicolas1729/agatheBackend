package com.ibp.web.rest;

import com.ibp.domain.RBUrl;
import com.ibp.service.RBUrlService;
import com.ibp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ibp.domain.RBUrl}.
 */
@RestController
@RequestMapping("/api")
public class RBUrlResource {

    private final Logger log = LoggerFactory.getLogger(RBUrlResource.class);

    private static final String ENTITY_NAME = "rBUrl";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RBUrlService rBUrlService;

    public RBUrlResource(RBUrlService rBUrlService) {
        this.rBUrlService = rBUrlService;
    }

    /**
     * {@code POST  /rb-urls} : Create a new rBUrl.
     *
     * @param rBUrl the rBUrl to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rBUrl, or with status {@code 400 (Bad Request)} if the rBUrl has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rb-urls")
    public ResponseEntity<RBUrl> createRBUrl(@Valid @RequestBody RBUrl rBUrl) throws URISyntaxException {
        log.debug("REST request to save RBUrl : {}", rBUrl);
        if (rBUrl.getId() != null) {
            throw new BadRequestAlertException("A new rBUrl cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RBUrl result = rBUrlService.save(rBUrl);
        return ResponseEntity.created(new URI("/api/rb-urls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rb-urls} : Updates an existing rBUrl.
     *
     * @param rBUrl the rBUrl to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rBUrl,
     * or with status {@code 400 (Bad Request)} if the rBUrl is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rBUrl couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rb-urls")
    public ResponseEntity<RBUrl> updateRBUrl(@Valid @RequestBody RBUrl rBUrl) throws URISyntaxException {
        log.debug("REST request to update RBUrl : {}", rBUrl);
        if (rBUrl.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RBUrl result = rBUrlService.save(rBUrl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rBUrl.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rb-urls} : get all the rBUrls.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rBUrls in body.
     */
    @GetMapping("/rb-urls")
    public List<RBUrl> getAllRBUrls() {
        log.debug("REST request to get all RBUrls");
        return rBUrlService.findAll();
    }

    /**
     * {@code GET  /rb-urls/:id} : get the "id" rBUrl.
     *
     * @param id the id of the rBUrl to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rBUrl, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rb-urls/{id}")
    public ResponseEntity<RBUrl> getRBUrl(@PathVariable Long id) {
        log.debug("REST request to get RBUrl : {}", id);
        Optional<RBUrl> rBUrl = rBUrlService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rBUrl);
    }

    /**
     * {@code DELETE  /rb-urls/:id} : delete the "id" rBUrl.
     *
     * @param id the id of the rBUrl to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rb-urls/{id}")
    public ResponseEntity<Void> deleteRBUrl(@PathVariable Long id) {
        log.debug("REST request to delete RBUrl : {}", id);
        rBUrlService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
