package com.anelsoftware.beer.web.rest;
import com.anelsoftware.beer.service.ProductoEnvaseService;
import com.anelsoftware.beer.web.rest.errors.BadRequestAlertException;
import com.anelsoftware.beer.web.rest.util.HeaderUtil;
import com.anelsoftware.beer.web.rest.util.PaginationUtil;
import com.anelsoftware.beer.service.dto.ProductoEnvaseDTO;
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
 * REST controller for managing ProductoEnvase.
 */
@RestController
@RequestMapping("/api")
public class ProductoEnvaseResource {

    private final Logger log = LoggerFactory.getLogger(ProductoEnvaseResource.class);

    private static final String ENTITY_NAME = "productoEnvase";

    private final ProductoEnvaseService productoEnvaseService;

    public ProductoEnvaseResource(ProductoEnvaseService productoEnvaseService) {
        this.productoEnvaseService = productoEnvaseService;
    }

    /**
     * POST  /producto-envases : Create a new productoEnvase.
     *
     * @param productoEnvaseDTO the productoEnvaseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productoEnvaseDTO, or with status 400 (Bad Request) if the productoEnvase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/producto-envases")
    public ResponseEntity<ProductoEnvaseDTO> createProductoEnvase(@RequestBody ProductoEnvaseDTO productoEnvaseDTO) throws URISyntaxException {
        log.debug("REST request to save ProductoEnvase : {}", productoEnvaseDTO);
        if (productoEnvaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new productoEnvase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoEnvaseDTO result = productoEnvaseService.save(productoEnvaseDTO);
        return ResponseEntity.created(new URI("/api/producto-envases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /producto-envases : Updates an existing productoEnvase.
     *
     * @param productoEnvaseDTO the productoEnvaseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productoEnvaseDTO,
     * or with status 400 (Bad Request) if the productoEnvaseDTO is not valid,
     * or with status 500 (Internal Server Error) if the productoEnvaseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/producto-envases")
    public ResponseEntity<ProductoEnvaseDTO> updateProductoEnvase(@RequestBody ProductoEnvaseDTO productoEnvaseDTO) throws URISyntaxException {
        log.debug("REST request to update ProductoEnvase : {}", productoEnvaseDTO);
        if (productoEnvaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductoEnvaseDTO result = productoEnvaseService.save(productoEnvaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productoEnvaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /producto-envases : get all the productoEnvases.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of productoEnvases in body
     */
    @GetMapping("/producto-envases")
    public ResponseEntity<List<ProductoEnvaseDTO>> getAllProductoEnvases(Pageable pageable) {
        log.debug("REST request to get a page of ProductoEnvases");
        Page<ProductoEnvaseDTO> page = productoEnvaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/producto-envases");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /producto-envases/:id : get the "id" productoEnvase.
     *
     * @param id the id of the productoEnvaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productoEnvaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/producto-envases/{id}")
    public ResponseEntity<ProductoEnvaseDTO> getProductoEnvase(@PathVariable Long id) {
        log.debug("REST request to get ProductoEnvase : {}", id);
        Optional<ProductoEnvaseDTO> productoEnvaseDTO = productoEnvaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoEnvaseDTO);
    }

    /**
     * DELETE  /producto-envases/:id : delete the "id" productoEnvase.
     *
     * @param id the id of the productoEnvaseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/producto-envases/{id}")
    public ResponseEntity<Void> deleteProductoEnvase(@PathVariable Long id) {
        log.debug("REST request to delete ProductoEnvase : {}", id);
        productoEnvaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/producto-envases?query=:query : search for the productoEnvase corresponding
     * to the query.
     *
     * @param query the query of the productoEnvase search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/producto-envases")
    public ResponseEntity<List<ProductoEnvaseDTO>> searchProductoEnvases(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProductoEnvases for query {}", query);
        Page<ProductoEnvaseDTO> page = productoEnvaseService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/producto-envases");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
