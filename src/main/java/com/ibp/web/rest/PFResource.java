package com.ibp.web.rest;

import com.ibp.domain.PF;
import com.ibp.service.PFService;
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
 * REST controller for managing {@link com.ibp.domain.PF}.
 */
@RestController
@RequestMapping("/api")
public class PFResource {

    private final Logger log = LoggerFactory.getLogger(PFResource.class);

    private static final String ENTITY_NAME = "pF";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PFService pFService;

    public PFResource(PFService pFService) {
        this.pFService = pFService;
    }

    /**
     * {@code POST  /pfs} : Create a new pF.
     *
     * @param pF the pF to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pF, or with status {@code 400 (Bad Request)} if the pF has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pfs")
    public ResponseEntity<PF> createPF(@Valid @RequestBody PF pF) throws URISyntaxException {
        log.debug("REST request to save PF : {}", pF);
        if (pF.getId() != null) {
            throw new BadRequestAlertException("A new pF cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PF result = pFService.save(pF);
        return ResponseEntity.created(new URI("/api/pfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pfs} : Updates an existing pF.
     *
     * @param pF the pF to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pF,
     * or with status {@code 400 (Bad Request)} if the pF is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pF couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pfs")
    public ResponseEntity<PF> updatePF(@Valid @RequestBody PF pF) throws URISyntaxException {
        log.debug("REST request to update PF : {}", pF);
        if (pF.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PF result = pFService.save(pF);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pF.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pfs} : get all the pFS.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pFS in body.
     */
    @GetMapping("/pfs")
    public List<PF> getAllPFS() {
        log.debug("REST request to get all PFS");
        return pFService.findAll();
    }

    /**
     * {@code GET  /pfs/:id} : get the "id" pF.
     *
     * @param id the id of the pF to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pF, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pfs/{id}")
    public ResponseEntity<PF> getPF(@PathVariable Long id) {
        log.debug("REST request to get PF : {}", id);
        Optional<PF> pF = pFService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pF);
    }

    /**
     * {@code DELETE  /pfs/:id} : delete the "id" pF.
     *
     * @param id the id of the pF to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pfs/{id}")
    public ResponseEntity<Void> deletePF(@PathVariable Long id) {
        log.debug("REST request to delete PF : {}", id);
        pFService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
