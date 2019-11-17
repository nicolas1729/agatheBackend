package com.ibp.web.rest;

import com.ibp.domain.VersionCommunautaire;
import com.ibp.service.VersionCommunautaireService;
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
 * REST controller for managing {@link com.ibp.domain.VersionCommunautaire}.
 */
@RestController
@RequestMapping("/api")
public class VersionCommunautaireResource {

    private final Logger log = LoggerFactory.getLogger(VersionCommunautaireResource.class);

    private static final String ENTITY_NAME = "versionCommunautaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VersionCommunautaireService versionCommunautaireService;

    public VersionCommunautaireResource(VersionCommunautaireService versionCommunautaireService) {
        this.versionCommunautaireService = versionCommunautaireService;
    }

    /**
     * {@code POST  /version-communautaires} : Create a new versionCommunautaire.
     *
     * @param versionCommunautaire the versionCommunautaire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new versionCommunautaire, or with status {@code 400 (Bad Request)} if the versionCommunautaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/version-communautaires")
    public ResponseEntity<VersionCommunautaire> createVersionCommunautaire(@Valid @RequestBody VersionCommunautaire versionCommunautaire) throws URISyntaxException {
        log.debug("REST request to save VersionCommunautaire : {}", versionCommunautaire);
        if (versionCommunautaire.getId() != null) {
            throw new BadRequestAlertException("A new versionCommunautaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VersionCommunautaire result = versionCommunautaireService.save(versionCommunautaire);
        return ResponseEntity.created(new URI("/api/version-communautaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /version-communautaires} : Updates an existing versionCommunautaire.
     *
     * @param versionCommunautaire the versionCommunautaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated versionCommunautaire,
     * or with status {@code 400 (Bad Request)} if the versionCommunautaire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the versionCommunautaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/version-communautaires")
    public ResponseEntity<VersionCommunautaire> updateVersionCommunautaire(@Valid @RequestBody VersionCommunautaire versionCommunautaire) throws URISyntaxException {
        log.debug("REST request to update VersionCommunautaire : {}", versionCommunautaire);
        if (versionCommunautaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VersionCommunautaire result = versionCommunautaireService.save(versionCommunautaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, versionCommunautaire.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /version-communautaires} : get all the versionCommunautaires.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of versionCommunautaires in body.
     */
    @GetMapping("/version-communautaires")
    public List<VersionCommunautaire> getAllVersionCommunautaires() {
        log.debug("REST request to get all VersionCommunautaires");
        return versionCommunautaireService.findAll();
    }

    /**
     * {@code GET  /version-communautaires/:id} : get the "id" versionCommunautaire.
     *
     * @param id the id of the versionCommunautaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the versionCommunautaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/version-communautaires/{id}")
    public ResponseEntity<VersionCommunautaire> getVersionCommunautaire(@PathVariable Long id) {
        log.debug("REST request to get VersionCommunautaire : {}", id);
        Optional<VersionCommunautaire> versionCommunautaire = versionCommunautaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(versionCommunautaire);
    }

    /**
     * {@code DELETE  /version-communautaires/:id} : delete the "id" versionCommunautaire.
     *
     * @param id the id of the versionCommunautaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/version-communautaires/{id}")
    public ResponseEntity<Void> deleteVersionCommunautaire(@PathVariable Long id) {
        log.debug("REST request to delete VersionCommunautaire : {}", id);
        versionCommunautaireService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
