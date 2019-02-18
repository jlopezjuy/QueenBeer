package com.anelsoftware.beer.web.rest;

import com.anelsoftware.beer.service.EnvaseService;
import com.anelsoftware.beer.web.rest.errors.BadRequestAlertException;
import com.anelsoftware.beer.web.rest.util.HeaderUtil;
import com.anelsoftware.beer.web.rest.util.PaginationUtil;
import com.anelsoftware.beer.service.dto.EnvaseDTO;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Envase.
 */
@RestController
@RequestMapping("/api")
public class EnvaseResource {

    private final Logger log = LoggerFactory.getLogger(EnvaseResource.class);

    private static final String ENTITY_NAME = "envase";

    private final EnvaseService envaseService;

    public EnvaseResource(EnvaseService envaseService) {
        this.envaseService = envaseService;
    }

    /**
     * POST  /envases : Create a new envase.
     *
     * @param envaseDTO the envaseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new envaseDTO, or with status 400 (Bad Request) if the envase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/envases")
    @Timed
    public ResponseEntity<EnvaseDTO> createEnvase(@RequestBody EnvaseDTO envaseDTO) throws URISyntaxException {
        log.debug("REST request to save Envase : {}", envaseDTO);
        if (envaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new envase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnvaseDTO result = envaseService.save(envaseDTO);
        return ResponseEntity.created(new URI("/api/envases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /envases : Updates an existing envase.
     *
     * @param envaseDTO the envaseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated envaseDTO,
     * or with status 400 (Bad Request) if the envaseDTO is not valid,
     * or with status 500 (Internal Server Error) if the envaseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/envases")
    @Timed
    public ResponseEntity<EnvaseDTO> updateEnvase(@RequestBody EnvaseDTO envaseDTO) throws URISyntaxException {
        log.debug("REST request to update Envase : {}", envaseDTO);
        if (envaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnvaseDTO result = envaseService.save(envaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, envaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /envases : get all the envases.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of envases in body
     */
    @GetMapping("/envases")
    @Timed
    public ResponseEntity<List<EnvaseDTO>> getAllEnvases(Pageable pageable) {
        log.debug("REST request to get a page of Envases");
        Page<EnvaseDTO> page = envaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/envases");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /envases/:id : get the "id" envase.
     *
     * @param id the id of the envaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the envaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/envases/{id}")
    @Timed
    public ResponseEntity<EnvaseDTO> getEnvase(@PathVariable Long id) {
        log.debug("REST request to get Envase : {}", id);
        Optional<EnvaseDTO> envaseDTO = envaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(envaseDTO);
    }

    /**
     * DELETE  /envases/:id : delete the "id" envase.
     *
     * @param id the id of the envaseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/envases/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnvase(@PathVariable Long id) {
        log.debug("REST request to delete Envase : {}", id);
        envaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/envases?query=:query : search for the envase corresponding
     * to the query.
     *
     * @param query the query of the envase search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/envases")
    @Timed
    public ResponseEntity<List<EnvaseDTO>> searchEnvases(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Envases for query {}", query);
        Page<EnvaseDTO> page = envaseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/envases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /envases : get all the envases by producto.
     *
     * @param productoId the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of envases in body
     */
    @GetMapping("/envases/producto/{productoId}")
    @Timed
    public ResponseEntity<List<EnvaseDTO>> getAllEnvasesByProducto(@PathVariable Long productoId) {
        log.debug("REST request to get a page of Envases");
        List<EnvaseDTO> page = envaseService.findAllByProductoId(productoId);
        return ResponseEntity.ok().body(page);
    }

}
