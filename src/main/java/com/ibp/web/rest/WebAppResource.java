package com.ibp.web.rest;

import com.ibp.domain.WebApp;
import com.ibp.service.WebAppService;
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
 * REST controller for managing {@link com.ibp.domain.WebApp}.
 */
@RestController
@RequestMapping("/api")
public class WebAppResource {

    private final Logger log = LoggerFactory.getLogger(WebAppResource.class);

    private static final String ENTITY_NAME = "webApp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WebAppService webAppService;

    public WebAppResource(WebAppService webAppService) {
        this.webAppService = webAppService;
    }

    /**
     * {@code POST  /web-apps} : Create a new webApp.
     *
     * @param webApp the webApp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new webApp, or with status {@code 400 (Bad Request)} if the webApp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/web-apps")
    public ResponseEntity<WebApp> createWebApp(@Valid @RequestBody WebApp webApp) throws URISyntaxException {
        log.debug("REST request to save WebApp : {}", webApp);
        if (webApp.getId() != null) {
            throw new BadRequestAlertException("A new webApp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WebApp result = webAppService.save(webApp);
        return ResponseEntity.created(new URI("/api/web-apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /web-apps} : Updates an existing webApp.
     *
     * @param webApp the webApp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated webApp,
     * or with status {@code 400 (Bad Request)} if the webApp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the webApp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/web-apps")
    public ResponseEntity<WebApp> updateWebApp(@Valid @RequestBody WebApp webApp) throws URISyntaxException {
        log.debug("REST request to update WebApp : {}", webApp);
        if (webApp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WebApp result = webAppService.save(webApp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, webApp.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /web-apps} : get all the webApps.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of webApps in body.
     */
    @GetMapping("/web-apps")
    public List<WebApp> getAllWebApps() {
        log.debug("REST request to get all WebApps");
        return webAppService.findAll();
    }

    /**
     * {@code GET  /web-apps/:id} : get the "id" webApp.
     *
     * @param id the id of the webApp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the webApp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/web-apps/{id}")
    public ResponseEntity<WebApp> getWebApp(@PathVariable Long id) {
        log.debug("REST request to get WebApp : {}", id);
        Optional<WebApp> webApp = webAppService.findOne(id);
        return ResponseUtil.wrapOrNotFound(webApp);
    }

    /**
     * {@code DELETE  /web-apps/:id} : delete the "id" webApp.
     *
     * @param id the id of the webApp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/web-apps/{id}")
    public ResponseEntity<Void> deleteWebApp(@PathVariable Long id) {
        log.debug("REST request to delete WebApp : {}", id);
        webAppService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
