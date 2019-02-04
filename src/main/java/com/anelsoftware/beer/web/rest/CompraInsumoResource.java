package com.anelsoftware.beer.web.rest;

import com.anelsoftware.beer.service.CompraInsumoService;
import com.anelsoftware.beer.web.rest.errors.BadRequestAlertException;
import com.anelsoftware.beer.web.rest.util.HeaderUtil;
import com.anelsoftware.beer.web.rest.util.PaginationUtil;
import com.anelsoftware.beer.service.dto.CompraInsumoDTO;
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
 * REST controller for managing CompraInsumo.
 */
@RestController
@RequestMapping("/api")
public class CompraInsumoResource {

    private final Logger log = LoggerFactory.getLogger(CompraInsumoResource.class);

    private static final String ENTITY_NAME = "compraInsumo";

    private CompraInsumoService compraInsumoService;

    public CompraInsumoResource(CompraInsumoService compraInsumoService) {
        this.compraInsumoService = compraInsumoService;
    }

    /**
     * POST  /compra-insumos : Create a new compraInsumo.
     *
     * @param compraInsumoDTO the compraInsumoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compraInsumoDTO, or with status 400 (Bad Request) if the compraInsumo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compra-insumos")
    @Timed
    public ResponseEntity<CompraInsumoDTO> createCompraInsumo(@RequestBody CompraInsumoDTO compraInsumoDTO) throws URISyntaxException {
        log.debug("REST request to save CompraInsumo : {}", compraInsumoDTO);
        if (compraInsumoDTO.getId() != null) {
            throw new BadRequestAlertException("A new compraInsumo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompraInsumoDTO result = compraInsumoService.save(compraInsumoDTO);
        return ResponseEntity.created(new URI("/api/compra-insumos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /compra-insumos : Updates an existing compraInsumo.
     *
     * @param compraInsumoDTO the compraInsumoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compraInsumoDTO,
     * or with status 400 (Bad Request) if the compraInsumoDTO is not valid,
     * or with status 500 (Internal Server Error) if the compraInsumoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compra-insumos")
    @Timed
    public ResponseEntity<CompraInsumoDTO> updateCompraInsumo(@RequestBody CompraInsumoDTO compraInsumoDTO) throws URISyntaxException {
        log.debug("REST request to update CompraInsumo : {}", compraInsumoDTO);
        if (compraInsumoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CompraInsumoDTO result = compraInsumoService.save(compraInsumoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compraInsumoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /compra-insumos : get all the compraInsumos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of compraInsumos in body
     */
    @GetMapping("/compra-insumos")
    @Timed
    public ResponseEntity<List<CompraInsumoDTO>> getAllCompraInsumos(Pageable pageable) {
        log.debug("REST request to get a page of CompraInsumos");
        Page<CompraInsumoDTO> page = compraInsumoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/compra-insumos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /compra-insumos/:id : get the "id" compraInsumo.
     *
     * @param id the id of the compraInsumoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compraInsumoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/compra-insumos/{id}")
    @Timed
    public ResponseEntity<CompraInsumoDTO> getCompraInsumo(@PathVariable Long id) {
        log.debug("REST request to get CompraInsumo : {}", id);
        Optional<CompraInsumoDTO> compraInsumoDTO = compraInsumoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compraInsumoDTO);
    }

    /**
     * DELETE  /compra-insumos/:id : delete the "id" compraInsumo.
     *
     * @param id the id of the compraInsumoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compra-insumos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompraInsumo(@PathVariable Long id) {
        log.debug("REST request to delete CompraInsumo : {}", id);
        compraInsumoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/compra-insumos/compra/{compraId}")
    @Timed
    public ResponseEntity<List<CompraInsumoDTO>> getAllCompraInsumosByCompra(@PathVariable Long compraId) {
        log.debug("REST request to get a page of CompraInsumos");
        List<CompraInsumoDTO> list = compraInsumoService.getAllByCompraId(compraId);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(list, "/api/compra-insumos");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * SEARCH  /_search/compra-insumos?query=:query : search for the compraInsumo corresponding
     * to the query.
     *
     * @param query the query of the compraInsumo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/compra-insumos")
    @Timed
    public ResponseEntity<List<CompraInsumoDTO>> searchCompraInsumos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CompraInsumos for query {}", query);
        Page<CompraInsumoDTO> page = compraInsumoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/compra-insumos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
