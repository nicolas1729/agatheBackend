package com.ibp.web.rest;

import com.ibp.domain.VersionPrivative;
import com.ibp.service.VersionPrivativeService;
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
 * REST controller for managing {@link com.ibp.domain.VersionPrivative}.
 */
@RestController
@RequestMapping("/api")
public class VersionPrivativeResource {

    private final Logger log = LoggerFactory.getLogger(VersionPrivativeResource.class);

    private static final String ENTITY_NAME = "versionPrivative";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VersionPrivativeService versionPrivativeService;

    public VersionPrivativeResource(VersionPrivativeService versionPrivativeService) {
        this.versionPrivativeService = versionPrivativeService;
    }

    /**
     * {@code POST  /version-privatives} : Create a new versionPrivative.
     *
     * @param versionPrivative the versionPrivative to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new versionPrivative, or with status {@code 400 (Bad Request)} if the versionPrivative has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/version-privatives")
    public ResponseEntity<VersionPrivative> createVersionPrivative(@Valid @RequestBody VersionPrivative versionPrivative) throws URISyntaxException {
        log.debug("REST request to save VersionPrivative : {}", versionPrivative);
        if (versionPrivative.getId() != null) {
            throw new BadRequestAlertException("A new versionPrivative cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VersionPrivative result = versionPrivativeService.save(versionPrivative);
        return ResponseEntity.created(new URI("/api/version-privatives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /version-privatives} : Updates an existing versionPrivative.
     *
     * @param versionPrivative the versionPrivative to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated versionPrivative,
     * or with status {@code 400 (Bad Request)} if the versionPrivative is not valid,
     * or with status {@code 500 (Internal Server Error)} if the versionPrivative couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/version-privatives")
    public ResponseEntity<VersionPrivative> updateVersionPrivative(@Valid @RequestBody VersionPrivative versionPrivative) throws URISyntaxException {
        log.debug("REST request to update VersionPrivative : {}", versionPrivative);
        if (versionPrivative.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VersionPrivative result = versionPrivativeService.save(versionPrivative);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, versionPrivative.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /version-privatives} : get all the versionPrivatives.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of versionPrivatives in body.
     */
    @GetMapping("/version-privatives")
    public List<VersionPrivative> getAllVersionPrivatives() {
        log.debug("REST request to get all VersionPrivatives");
        return versionPrivativeService.findAll();
    }

    /**
     * {@code GET  /version-privatives/:id} : get the "id" versionPrivative.
     *
     * @param id the id of the versionPrivative to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the versionPrivative, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/version-privatives/{id}")
    public ResponseEntity<VersionPrivative> getVersionPrivative(@PathVariable Long id) {
        log.debug("REST request to get VersionPrivative : {}", id);
        Optional<VersionPrivative> versionPrivative = versionPrivativeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(versionPrivative);
    }

    /**
     * {@code DELETE  /version-privatives/:id} : delete the "id" versionPrivative.
     *
     * @param id the id of the versionPrivative to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/version-privatives/{id}")
    public ResponseEntity<Void> deleteVersionPrivative(@PathVariable Long id) {
        log.debug("REST request to delete VersionPrivative : {}", id);
        versionPrivativeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
