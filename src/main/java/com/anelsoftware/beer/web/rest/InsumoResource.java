package com.anelsoftware.beer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anelsoftware.beer.service.InsumoService;
import com.anelsoftware.beer.web.rest.errors.BadRequestAlertException;
import com.anelsoftware.beer.web.rest.util.HeaderUtil;
import com.anelsoftware.beer.web.rest.util.PaginationUtil;
import com.anelsoftware.beer.service.dto.InsumoDTO;
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
 * REST controller for managing Insumo.
 */
@RestController
@RequestMapping("/api")
public class InsumoResource {

    private final Logger log = LoggerFactory.getLogger(InsumoResource.class);

    private static final String ENTITY_NAME = "insumo";

    private InsumoService insumoService;

    public InsumoResource(InsumoService insumoService) {
        this.insumoService = insumoService;
    }

    /**
     * POST  /insumos : Create a new insumo.
     *
     * @param insumoDTO the insumoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insumoDTO, or with status 400 (Bad Request) if the insumo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insumos")
    @Timed
    public ResponseEntity<InsumoDTO> createInsumo(@RequestBody InsumoDTO insumoDTO) throws URISyntaxException {
        log.debug("REST request to save Insumo : {}", insumoDTO);
        if (insumoDTO.getId() != null) {
            throw new BadRequestAlertException("A new insumo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsumoDTO result = insumoService.save(insumoDTO);
        return ResponseEntity.created(new URI("/api/insumos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insumos : Updates an existing insumo.
     *
     * @param insumoDTO the insumoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insumoDTO,
     * or with status 400 (Bad Request) if the insumoDTO is not valid,
     * or with status 500 (Internal Server Error) if the insumoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insumos")
    @Timed
    public ResponseEntity<InsumoDTO> updateInsumo(@RequestBody InsumoDTO insumoDTO) throws URISyntaxException {
        log.debug("REST request to update Insumo : {}", insumoDTO);
        if (insumoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InsumoDTO result = insumoService.save(insumoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, insumoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insumos : get all the insumos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of insumos in body
     */
    @GetMapping("/insumos")
    @Timed
    public ResponseEntity<List<InsumoDTO>> getAllInsumos(Pageable pageable) {
        log.debug("REST request to get a page of Insumos");
        Page<InsumoDTO> page = insumoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/insumos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/insumos/all")
    @Timed
    public ResponseEntity<List<InsumoDTO>> getAllInsumosWithOuthPage() {
        log.debug("REST request to get a page of Insumos");
        List<InsumoDTO> page = insumoService.findAll();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    /**
     * GET  /insumos/:id : get the "id" insumo.
     *
     * @param id the id of the insumoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insumoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/insumos/{id}")
    @Timed
    public ResponseEntity<InsumoDTO> getInsumo(@PathVariable Long id) {
        log.debug("REST request to get Insumo : {}", id);
        Optional<InsumoDTO> insumoDTO = insumoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(insumoDTO);
    }

    /**
     * DELETE  /insumos/:id : delete the "id" insumo.
     *
     * @param id the id of the insumoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insumos/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsumo(@PathVariable Long id) {
        log.debug("REST request to delete Insumo : {}", id);
        insumoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/insumos?query=:query : search for the insumo corresponding
     * to the query.
     *
     * @param query the query of the insumo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/insumos")
    @Timed
    public ResponseEntity<List<InsumoDTO>> searchInsumos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Insumos for query {}", query);
        Page<InsumoDTO> page = insumoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/insumos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}