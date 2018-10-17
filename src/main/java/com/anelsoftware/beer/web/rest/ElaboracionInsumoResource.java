package com.anelsoftware.beer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anelsoftware.beer.service.ElaboracionInsumoService;
import com.anelsoftware.beer.web.rest.errors.BadRequestAlertException;
import com.anelsoftware.beer.web.rest.util.HeaderUtil;
import com.anelsoftware.beer.web.rest.util.PaginationUtil;
import com.anelsoftware.beer.service.dto.ElaboracionInsumoDTO;
import io.github.jhipster.web.util.ResponseUtil;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ElaboracionInsumo.
 */
@RestController
@RequestMapping("/api")
public class ElaboracionInsumoResource {

    private final Logger log = LoggerFactory.getLogger(ElaboracionInsumoResource.class);

    private static final String ENTITY_NAME = "elaboracionInsumo";

    private ElaboracionInsumoService elaboracionInsumoService;

    public ElaboracionInsumoResource(ElaboracionInsumoService elaboracionInsumoService) {
        this.elaboracionInsumoService = elaboracionInsumoService;
    }

    /**
     * POST  /elaboracion-insumos : Create a new elaboracionInsumo.
     *
     * @param elaboracionInsumoDTO the elaboracionInsumoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new elaboracionInsumoDTO, or with status 400 (Bad Request) if the elaboracionInsumo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/elaboracion-insumos")
    @Timed
    public ResponseEntity<ElaboracionInsumoDTO> createElaboracionInsumo(@RequestBody ElaboracionInsumoDTO elaboracionInsumoDTO) throws URISyntaxException {
        log.debug("REST request to save ElaboracionInsumo : {}", elaboracionInsumoDTO);
        if (elaboracionInsumoDTO.getId() != null) {
            throw new BadRequestAlertException("A new elaboracionInsumo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElaboracionInsumoDTO result = elaboracionInsumoService.save(elaboracionInsumoDTO);
        return ResponseEntity.created(new URI("/api/elaboracion-insumos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /elaboracion-insumos : Updates an existing elaboracionInsumo.
     *
     * @param elaboracionInsumoDTO the elaboracionInsumoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated elaboracionInsumoDTO,
     * or with status 400 (Bad Request) if the elaboracionInsumoDTO is not valid,
     * or with status 500 (Internal Server Error) if the elaboracionInsumoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/elaboracion-insumos")
    @Timed
    public ResponseEntity<ElaboracionInsumoDTO> updateElaboracionInsumo(@RequestBody ElaboracionInsumoDTO elaboracionInsumoDTO) throws URISyntaxException {
        log.debug("REST request to update ElaboracionInsumo : {}", elaboracionInsumoDTO);
        if (elaboracionInsumoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ElaboracionInsumoDTO result = elaboracionInsumoService.save(elaboracionInsumoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, elaboracionInsumoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /elaboracion-insumos : get all the elaboracionInsumos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of elaboracionInsumos in body
     */
    @GetMapping("/elaboracion-insumos")
    @Timed
    public ResponseEntity<List<ElaboracionInsumoDTO>> getAllElaboracionInsumos(Pageable pageable) {
        log.debug("REST request to get a page of ElaboracionInsumos");
        Page<ElaboracionInsumoDTO> page = elaboracionInsumoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/elaboracion-insumos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /elaboracion-insumos/:id : get the "id" elaboracionInsumo.
     *
     * @param id the id of the elaboracionInsumoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the elaboracionInsumoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/elaboracion-insumos/{id}")
    @Timed
    public ResponseEntity<ElaboracionInsumoDTO> getElaboracionInsumo(@PathVariable Long id) {
        log.debug("REST request to get ElaboracionInsumo : {}", id);
        Optional<ElaboracionInsumoDTO> elaboracionInsumoDTO = elaboracionInsumoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(elaboracionInsumoDTO);
    }

    /**
     * DELETE  /elaboracion-insumos/:id : delete the "id" elaboracionInsumo.
     *
     * @param id the id of the elaboracionInsumoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/elaboracion-insumos/{id}")
    @Timed
    public ResponseEntity<Void> deleteElaboracionInsumo(@PathVariable Long id) {
        log.debug("REST request to delete ElaboracionInsumo : {}", id);
        elaboracionInsumoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/elaboracion-insumos?query=:query : search for the elaboracionInsumo corresponding
     * to the query.
     *
     * @param query the query of the elaboracionInsumo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/elaboracion-insumos")
    @Timed
    public ResponseEntity<List<ElaboracionInsumoDTO>> searchElaboracionInsumos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ElaboracionInsumos for query {}", query);
        Page<ElaboracionInsumoDTO> page = elaboracionInsumoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/elaboracion-insumos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
