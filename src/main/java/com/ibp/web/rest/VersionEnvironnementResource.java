package com.ibp.web.rest;

import com.ibp.domain.VersionEnvironnement;
import com.ibp.service.VersionEnvironnementService;
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
 * REST controller for managing {@link com.ibp.domain.VersionEnvironnement}.
 */
@RestController
@RequestMapping("/api")
public class VersionEnvironnementResource {

    private final Logger log = LoggerFactory.getLogger(VersionEnvironnementResource.class);

    private static final String ENTITY_NAME = "versionEnvironnement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VersionEnvironnementService versionEnvironnementService;

    public VersionEnvironnementResource(VersionEnvironnementService versionEnvironnementService) {
        this.versionEnvironnementService = versionEnvironnementService;
    }

    /**
     * {@code POST  /version-environnements} : Create a new versionEnvironnement.
     *
     * @param versionEnvironnement the versionEnvironnement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new versionEnvironnement, or with status {@code 400 (Bad Request)} if the versionEnvironnement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/version-environnements")
    public ResponseEntity<VersionEnvironnement> createVersionEnvironnement(@Valid @RequestBody VersionEnvironnement versionEnvironnement) throws URISyntaxException {
        log.debug("REST request to save VersionEnvironnement : {}", versionEnvironnement);
        if (versionEnvironnement.getId() != null) {
            throw new BadRequestAlertException("A new versionEnvironnement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VersionEnvironnement result = versionEnvironnementService.save(versionEnvironnement);
        return ResponseEntity.created(new URI("/api/version-environnements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /version-environnements} : Updates an existing versionEnvironnement.
     *
     * @param versionEnvironnement the versionEnvironnement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated versionEnvironnement,
     * or with status {@code 400 (Bad Request)} if the versionEnvironnement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the versionEnvironnement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/version-environnements")
    public ResponseEntity<VersionEnvironnement> updateVersionEnvironnement(@Valid @RequestBody VersionEnvironnement versionEnvironnement) throws URISyntaxException {
        log.debug("REST request to update VersionEnvironnement : {}", versionEnvironnement);
        if (versionEnvironnement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VersionEnvironnement result = versionEnvironnementService.save(versionEnvironnement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, versionEnvironnement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /version-environnements} : get all the versionEnvironnements.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of versionEnvironnements in body.
     */
    @GetMapping("/version-environnements")
    public List<VersionEnvironnement> getAllVersionEnvironnements() {
        log.debug("REST request to get all VersionEnvironnements");
        return versionEnvironnementService.findAll();
    }

    /**
     * {@code GET  /version-environnements/:id} : get the "id" versionEnvironnement.
     *
     * @param id the id of the versionEnvironnement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the versionEnvironnement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/version-environnements/{id}")
    public ResponseEntity<VersionEnvironnement> getVersionEnvironnement(@PathVariable Long id) {
        log.debug("REST request to get VersionEnvironnement : {}", id);
        Optional<VersionEnvironnement> versionEnvironnement = versionEnvironnementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(versionEnvironnement);
    }

    /**
     * {@code DELETE  /version-environnements/:id} : delete the "id" versionEnvironnement.
     *
     * @param id the id of the versionEnvironnement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/version-environnements/{id}")
    public ResponseEntity<Void> deleteVersionEnvironnement(@PathVariable Long id) {
        log.debug("REST request to delete VersionEnvironnement : {}", id);
        versionEnvironnementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
