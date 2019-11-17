package com.ibp.web.rest;

import com.ibp.domain.RBExe;
import com.ibp.service.RBExeService;
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
 * REST controller for managing {@link com.ibp.domain.RBExe}.
 */
@RestController
@RequestMapping("/api")
public class RBExeResource {

    private final Logger log = LoggerFactory.getLogger(RBExeResource.class);

    private static final String ENTITY_NAME = "rBExe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RBExeService rBExeService;

    public RBExeResource(RBExeService rBExeService) {
        this.rBExeService = rBExeService;
    }

    /**
     * {@code POST  /rb-exes} : Create a new rBExe.
     *
     * @param rBExe the rBExe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rBExe, or with status {@code 400 (Bad Request)} if the rBExe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rb-exes")
    public ResponseEntity<RBExe> createRBExe(@Valid @RequestBody RBExe rBExe) throws URISyntaxException {
        log.debug("REST request to save RBExe : {}", rBExe);
        if (rBExe.getId() != null) {
            throw new BadRequestAlertException("A new rBExe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RBExe result = rBExeService.save(rBExe);
        return ResponseEntity.created(new URI("/api/rb-exes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rb-exes} : Updates an existing rBExe.
     *
     * @param rBExe the rBExe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rBExe,
     * or with status {@code 400 (Bad Request)} if the rBExe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rBExe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rb-exes")
    public ResponseEntity<RBExe> updateRBExe(@Valid @RequestBody RBExe rBExe) throws URISyntaxException {
        log.debug("REST request to update RBExe : {}", rBExe);
        if (rBExe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RBExe result = rBExeService.save(rBExe);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rBExe.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rb-exes} : get all the rBExes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rBExes in body.
     */
    @GetMapping("/rb-exes")
    public List<RBExe> getAllRBExes() {
        log.debug("REST request to get all RBExes");
        return rBExeService.findAll();
    }

    /**
     * {@code GET  /rb-exes/:id} : get the "id" rBExe.
     *
     * @param id the id of the rBExe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rBExe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rb-exes/{id}")
    public ResponseEntity<RBExe> getRBExe(@PathVariable Long id) {
        log.debug("REST request to get RBExe : {}", id);
        Optional<RBExe> rBExe = rBExeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rBExe);
    }

    /**
     * {@code DELETE  /rb-exes/:id} : delete the "id" rBExe.
     *
     * @param id the id of the rBExe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rb-exes/{id}")
    public ResponseEntity<Void> deleteRBExe(@PathVariable Long id) {
        log.debug("REST request to delete RBExe : {}", id);
        rBExeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
