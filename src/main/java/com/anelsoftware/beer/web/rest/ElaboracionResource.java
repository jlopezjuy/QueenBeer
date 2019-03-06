package com.anelsoftware.beer.web.rest;

import com.anelsoftware.beer.service.ElaboracionService;
import com.anelsoftware.beer.web.rest.errors.BadRequestAlertException;
import com.anelsoftware.beer.web.rest.util.HeaderUtil;
import com.anelsoftware.beer.web.rest.util.PaginationUtil;
import com.anelsoftware.beer.service.dto.ElaboracionDTO;
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
 * REST controller for managing Elaboracion.
 */
@RestController
@RequestMapping("/api")
public class ElaboracionResource {

    private final Logger log = LoggerFactory.getLogger(ElaboracionResource.class);

    private static final String ENTITY_NAME = "elaboracion";

    private ElaboracionService elaboracionService;

    public ElaboracionResource(ElaboracionService elaboracionService) {
        this.elaboracionService = elaboracionService;
    }

    /**
     * POST  /elaboracions : Create a new elaboracion.
     *
     * @param elaboracionDTO the elaboracionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new elaboracionDTO, or with status 400 (Bad Request) if the elaboracion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/elaboracions")
    @Timed
    public ResponseEntity<ElaboracionDTO> createElaboracion(@RequestBody ElaboracionDTO elaboracionDTO) throws URISyntaxException {
        log.debug("REST request to save Elaboracion : {}", elaboracionDTO);
        if (elaboracionDTO.getId() != null) {
            throw new BadRequestAlertException("A new elaboracion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElaboracionDTO result = elaboracionService.save(elaboracionDTO);
        return ResponseEntity.created(new URI("/api/elaboracions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /elaboracions : Updates an existing elaboracion.
     *
     * @param elaboracionDTO the elaboracionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated elaboracionDTO,
     * or with status 400 (Bad Request) if the elaboracionDTO is not valid,
     * or with status 500 (Internal Server Error) if the elaboracionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/elaboracions")
    @Timed
    public ResponseEntity<ElaboracionDTO> updateElaboracion(@RequestBody ElaboracionDTO elaboracionDTO) throws URISyntaxException {
        log.debug("REST request to update Elaboracion : {}", elaboracionDTO);
        if (elaboracionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ElaboracionDTO result = elaboracionService.save(elaboracionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, elaboracionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /elaboracions : get all the elaboracions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of elaboracions in body
     */
    @GetMapping("/elaboracions")
    @Timed
    public ResponseEntity<List<ElaboracionDTO>> getAllElaboracions(Pageable pageable) {
        log.debug("REST request to get a page of Elaboracions");
        Page<ElaboracionDTO> page = elaboracionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/elaboracions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /elaboracions/:id : get the "id" elaboracion.
     *
     * @param id the id of the elaboracionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the elaboracionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/elaboracions/{id}")
    @Timed
    public ResponseEntity<ElaboracionDTO> getElaboracion(@PathVariable Long id) {
        log.debug("REST request to get Elaboracion : {}", id);
        Optional<ElaboracionDTO> elaboracionDTO = elaboracionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(elaboracionDTO);
    }

    /**
     * DELETE  /elaboracions/:id : delete the "id" elaboracion.
     *
     * @param id the id of the elaboracionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/elaboracions/{id}")
    @Timed
    public ResponseEntity<Void> deleteElaboracion(@PathVariable Long id) {
        log.debug("REST request to delete Elaboracion : {}", id);
        elaboracionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/elaboracions?query=:query : search for the elaboracion corresponding
     * to the query.
     *
     * @param query the query of the elaboracion search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/elaboracions")
    @Timed
    public ResponseEntity<List<ElaboracionDTO>> searchElaboracions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Elaboracions for query {}", query);
        Page<ElaboracionDTO> page = elaboracionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/elaboracions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
