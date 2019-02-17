package com.anelsoftware.beer.web.rest;
import com.anelsoftware.beer.service.DetalleVentaService;
import com.anelsoftware.beer.web.rest.errors.BadRequestAlertException;
import com.anelsoftware.beer.web.rest.util.HeaderUtil;
import com.anelsoftware.beer.web.rest.util.PaginationUtil;
import com.anelsoftware.beer.service.dto.DetalleVentaDTO;
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
 * REST controller for managing DetalleVenta.
 */
@RestController
@RequestMapping("/api")
public class DetalleVentaResource {

    private final Logger log = LoggerFactory.getLogger(DetalleVentaResource.class);

    private static final String ENTITY_NAME = "detalleVenta";

    private final DetalleVentaService detalleVentaService;

    public DetalleVentaResource(DetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    /**
     * POST  /detalle-ventas : Create a new detalleVenta.
     *
     * @param detalleVentaDTO the detalleVentaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new detalleVentaDTO, or with status 400 (Bad Request) if the detalleVenta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/detalle-ventas")
    public ResponseEntity<DetalleVentaDTO> createDetalleVenta(@RequestBody DetalleVentaDTO detalleVentaDTO) throws URISyntaxException {
        log.debug("REST request to save DetalleVenta : {}", detalleVentaDTO);
        if (detalleVentaDTO.getId() != null) {
            throw new BadRequestAlertException("A new detalleVenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetalleVentaDTO result = detalleVentaService.save(detalleVentaDTO);
        return ResponseEntity.created(new URI("/api/detalle-ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /detalle-ventas : Updates an existing detalleVenta.
     *
     * @param detalleVentaDTO the detalleVentaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated detalleVentaDTO,
     * or with status 400 (Bad Request) if the detalleVentaDTO is not valid,
     * or with status 500 (Internal Server Error) if the detalleVentaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/detalle-ventas")
    public ResponseEntity<DetalleVentaDTO> updateDetalleVenta(@RequestBody DetalleVentaDTO detalleVentaDTO) throws URISyntaxException {
        log.debug("REST request to update DetalleVenta : {}", detalleVentaDTO);
        if (detalleVentaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DetalleVentaDTO result = detalleVentaService.save(detalleVentaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, detalleVentaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /detalle-ventas : get all the detalleVentas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of detalleVentas in body
     */
    @GetMapping("/detalle-ventas")
    public ResponseEntity<List<DetalleVentaDTO>> getAllDetalleVentas(Pageable pageable) {
        log.debug("REST request to get a page of DetalleVentas");
        Page<DetalleVentaDTO> page = detalleVentaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/detalle-ventas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /detalle-ventas/:id : get the "id" detalleVenta.
     *
     * @param id the id of the detalleVentaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the detalleVentaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/detalle-ventas/{id}")
    public ResponseEntity<DetalleVentaDTO> getDetalleVenta(@PathVariable Long id) {
        log.debug("REST request to get DetalleVenta : {}", id);
        Optional<DetalleVentaDTO> detalleVentaDTO = detalleVentaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(detalleVentaDTO);
    }

    /**
     * DELETE  /detalle-ventas/:id : delete the "id" detalleVenta.
     *
     * @param id the id of the detalleVentaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/detalle-ventas/{id}")
    public ResponseEntity<Void> deleteDetalleVenta(@PathVariable Long id) {
        log.debug("REST request to delete DetalleVenta : {}", id);
        detalleVentaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/detalle-ventas?query=:query : search for the detalleVenta corresponding
     * to the query.
     *
     * @param query the query of the detalleVenta search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/detalle-ventas")
    public ResponseEntity<List<DetalleVentaDTO>> searchDetalleVentas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DetalleVentas for query {}", query);
        Page<DetalleVentaDTO> page = detalleVentaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/detalle-ventas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
