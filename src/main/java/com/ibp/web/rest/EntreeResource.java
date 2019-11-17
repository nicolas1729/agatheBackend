package com.ibp.web.rest;

import com.ibp.domain.Entree;
import com.ibp.repository.EntreeRepository;
import com.ibp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ibp.domain.Entree}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EntreeResource {

    private final Logger log = LoggerFactory.getLogger(EntreeResource.class);

    private static final String ENTITY_NAME = "entree";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntreeRepository entreeRepository;

    public EntreeResource(EntreeRepository entreeRepository) {
        this.entreeRepository = entreeRepository;
    }

    /**
     * {@code POST  /entrees} : Create a new entree.
     *
     * @param entree the entree to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entree, or with status {@code 400 (Bad Request)} if the entree has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entrees")
    public ResponseEntity<Entree> createEntree(@Valid @RequestBody Entree entree) throws URISyntaxException {
        log.debug("REST request to save Entree : {}", entree);
        if (entree.getId() != null) {
            throw new BadRequestAlertException("A new entree cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Entree result = entreeRepository.save(entree);
        return ResponseEntity.created(new URI("/api/entrees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entrees} : Updates an existing entree.
     *
     * @param entree the entree to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entree,
     * or with status {@code 400 (Bad Request)} if the entree is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entree couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entrees")
    public ResponseEntity<Entree> updateEntree(@Valid @RequestBody Entree entree) throws URISyntaxException {
        log.debug("REST request to update Entree : {}", entree);
        if (entree.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Entree result = entreeRepository.save(entree);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, entree.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entrees} : get all the entrees.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entrees in body.
     */
    @GetMapping("/entrees")
    public ResponseEntity<List<Entree>> getAllEntrees(Pageable pageable) {
        log.debug("REST request to get a page of Entrees");
        Page<Entree> page = entreeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entrees/:id} : get the "id" entree.
     *
     * @param id the id of the entree to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entree, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entrees/{id}")
    public ResponseEntity<Entree> getEntree(@PathVariable Long id) {
        log.debug("REST request to get Entree : {}", id);
        Optional<Entree> entree = entreeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(entree);
    }

    /**
     * {@code DELETE  /entrees/:id} : delete the "id" entree.
     *
     * @param id the id of the entree to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entrees/{id}")
    public ResponseEntity<Void> deleteEntree(@PathVariable Long id) {
        log.debug("REST request to delete Entree : {}", id);
        entreeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
