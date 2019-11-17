package com.ibp.web.rest;

import com.ibp.domain.Environnement;
import com.ibp.service.EnvironnementService;
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
 * REST controller for managing {@link com.ibp.domain.Environnement}.
 */
@RestController
@RequestMapping("/api")
public class EnvironnementResource {

    private final Logger log = LoggerFactory.getLogger(EnvironnementResource.class);

    private static final String ENTITY_NAME = "environnement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnvironnementService environnementService;

    public EnvironnementResource(EnvironnementService environnementService) {
        this.environnementService = environnementService;
    }

    /**
     * {@code POST  /environnements} : Create a new environnement.
     *
     * @param environnement the environnement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new environnement, or with status {@code 400 (Bad Request)} if the environnement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/environnements")
    public ResponseEntity<Environnement> createEnvironnement(@Valid @RequestBody Environnement environnement) throws URISyntaxException {
        log.debug("REST request to save Environnement : {}", environnement);
        if (environnement.getId() != null) {
            throw new BadRequestAlertException("A new environnement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Environnement result = environnementService.save(environnement);
        return ResponseEntity.created(new URI("/api/environnements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /environnements} : Updates an existing environnement.
     *
     * @param environnement the environnement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated environnement,
     * or with status {@code 400 (Bad Request)} if the environnement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the environnement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/environnements")
    public ResponseEntity<Environnement> updateEnvironnement(@Valid @RequestBody Environnement environnement) throws URISyntaxException {
        log.debug("REST request to update Environnement : {}", environnement);
        if (environnement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Environnement result = environnementService.save(environnement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, environnement.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /environnements} : get all the environnements.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of environnements in body.
     */
    @GetMapping("/environnements")
    public List<Environnement> getAllEnvironnements() {
        log.debug("REST request to get all Environnements");
        return environnementService.findAll();
    }

    /**
     * {@code GET  /environnements/:id} : get the "id" environnement.
     *
     * @param id the id of the environnement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the environnement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/environnements/{id}")
    public ResponseEntity<Environnement> getEnvironnement(@PathVariable Long id) {
        log.debug("REST request to get Environnement : {}", id);
        Optional<Environnement> environnement = environnementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(environnement);
    }

    /**
     * {@code DELETE  /environnements/:id} : delete the "id" environnement.
     *
     * @param id the id of the environnement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/environnements/{id}")
    public ResponseEntity<Void> deleteEnvironnement(@PathVariable Long id) {
        log.debug("REST request to delete Environnement : {}", id);
        environnementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
