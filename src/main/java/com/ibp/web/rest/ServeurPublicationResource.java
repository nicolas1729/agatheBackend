package com.ibp.web.rest;

import com.ibp.domain.ServeurPublication;
import com.ibp.service.ServeurPublicationService;
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
 * REST controller for managing {@link com.ibp.domain.ServeurPublication}.
 */
@RestController
@RequestMapping("/api")
public class ServeurPublicationResource {

    private final Logger log = LoggerFactory.getLogger(ServeurPublicationResource.class);

    private static final String ENTITY_NAME = "serveurPublication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServeurPublicationService serveurPublicationService;

    public ServeurPublicationResource(ServeurPublicationService serveurPublicationService) {
        this.serveurPublicationService = serveurPublicationService;
    }

    /**
     * {@code POST  /serveur-publications} : Create a new serveurPublication.
     *
     * @param serveurPublication the serveurPublication to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serveurPublication, or with status {@code 400 (Bad Request)} if the serveurPublication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/serveur-publications")
    public ResponseEntity<ServeurPublication> createServeurPublication(@Valid @RequestBody ServeurPublication serveurPublication) throws URISyntaxException {
        log.debug("REST request to save ServeurPublication : {}", serveurPublication);
        if (serveurPublication.getId() != null) {
            throw new BadRequestAlertException("A new serveurPublication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServeurPublication result = serveurPublicationService.save(serveurPublication);
        return ResponseEntity.created(new URI("/api/serveur-publications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /serveur-publications} : Updates an existing serveurPublication.
     *
     * @param serveurPublication the serveurPublication to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serveurPublication,
     * or with status {@code 400 (Bad Request)} if the serveurPublication is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serveurPublication couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/serveur-publications")
    public ResponseEntity<ServeurPublication> updateServeurPublication(@Valid @RequestBody ServeurPublication serveurPublication) throws URISyntaxException {
        log.debug("REST request to update ServeurPublication : {}", serveurPublication);
        if (serveurPublication.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServeurPublication result = serveurPublicationService.save(serveurPublication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serveurPublication.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /serveur-publications} : get all the serveurPublications.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serveurPublications in body.
     */
    @GetMapping("/serveur-publications")
    public List<ServeurPublication> getAllServeurPublications() {
        log.debug("REST request to get all ServeurPublications");
        return serveurPublicationService.findAll();
    }

    /**
     * {@code GET  /serveur-publications/:id} : get the "id" serveurPublication.
     *
     * @param id the id of the serveurPublication to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serveurPublication, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/serveur-publications/{id}")
    public ResponseEntity<ServeurPublication> getServeurPublication(@PathVariable Long id) {
        log.debug("REST request to get ServeurPublication : {}", id);
        Optional<ServeurPublication> serveurPublication = serveurPublicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serveurPublication);
    }

    /**
     * {@code DELETE  /serveur-publications/:id} : delete the "id" serveurPublication.
     *
     * @param id the id of the serveurPublication to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/serveur-publications/{id}")
    public ResponseEntity<Void> deleteServeurPublication(@PathVariable Long id) {
        log.debug("REST request to delete ServeurPublication : {}", id);
        serveurPublicationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
