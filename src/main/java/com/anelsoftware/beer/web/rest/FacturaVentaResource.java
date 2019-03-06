package com.anelsoftware.beer.web.rest;
import com.anelsoftware.beer.service.FacturaVentaService;
import com.anelsoftware.beer.web.rest.errors.BadRequestAlertException;
import com.anelsoftware.beer.web.rest.util.HeaderUtil;
import com.anelsoftware.beer.web.rest.util.PaginationUtil;
import com.anelsoftware.beer.service.dto.FacturaVentaDTO;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing FacturaVenta.
 */
@RestController
@RequestMapping("/api")
public class FacturaVentaResource {

    private final Logger log = LoggerFactory.getLogger(FacturaVentaResource.class);

    private static final String ENTITY_NAME = "facturaVenta";

    private final FacturaVentaService facturaVentaService;

    public FacturaVentaResource(FacturaVentaService facturaVentaService) {
        this.facturaVentaService = facturaVentaService;
    }

    /**
     * POST  /factura-ventas : Create a new facturaVenta.
     *
     * @param facturaVentaDTO the facturaVentaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facturaVentaDTO, or with status 400 (Bad Request) if the facturaVenta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/factura-ventas")
    public ResponseEntity<FacturaVentaDTO> createFacturaVenta(@RequestBody FacturaVentaDTO facturaVentaDTO) throws URISyntaxException {
        log.debug("REST request to save FacturaVenta : {}", facturaVentaDTO);
        if (facturaVentaDTO.getId() != null) {
            throw new BadRequestAlertException("A new facturaVenta cannot already have an ID", ENTITY_NAME, "idexists");
        }
//        if (Objects.isNull(facturaVentaDTO.getClienteId())) {
//            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
//        }
        FacturaVentaDTO result = facturaVentaService.save(facturaVentaDTO);
        return ResponseEntity.created(new URI("/api/factura-ventas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /factura-ventas : Updates an existing facturaVenta.
     *
     * @param facturaVentaDTO the facturaVentaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facturaVentaDTO,
     * or with status 400 (Bad Request) if the facturaVentaDTO is not valid,
     * or with status 500 (Internal Server Error) if the facturaVentaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/factura-ventas")
    public ResponseEntity<FacturaVentaDTO> updateFacturaVenta(@RequestBody FacturaVentaDTO facturaVentaDTO) throws URISyntaxException {
        log.debug("REST request to update FacturaVenta : {}", facturaVentaDTO);
        if (facturaVentaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FacturaVentaDTO result = facturaVentaService.save(facturaVentaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facturaVentaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /factura-ventas : get all the facturaVentas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of facturaVentas in body
     */
    @GetMapping("/factura-ventas")
    public ResponseEntity<List<FacturaVentaDTO>> getAllFacturaVentas(Pageable pageable) {
        log.debug("REST request to get a page of FacturaVentas");
        Page<FacturaVentaDTO> page = facturaVentaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/factura-ventas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /factura-ventas/:id : get the "id" facturaVenta.
     *
     * @param id the id of the facturaVentaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facturaVentaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/factura-ventas/{id}")
    public ResponseEntity<FacturaVentaDTO> getFacturaVenta(@PathVariable Long id) {
        log.debug("REST request to get FacturaVenta : {}", id);
        Optional<FacturaVentaDTO> facturaVentaDTO = facturaVentaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facturaVentaDTO);
    }

    /**
     * DELETE  /factura-ventas/:id : delete the "id" facturaVenta.
     *
     * @param id the id of the facturaVentaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/factura-ventas/{id}")
    public ResponseEntity<Void> deleteFacturaVenta(@PathVariable Long id) {
        log.debug("REST request to delete FacturaVenta : {}", id);
        facturaVentaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/factura-ventas?query=:query : search for the facturaVenta corresponding
     * to the query.
     *
     * @param query the query of the facturaVenta search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/factura-ventas")
    public ResponseEntity<List<FacturaVentaDTO>> searchFacturaVentas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of FacturaVentas for query {}", query);
        Page<FacturaVentaDTO> page = facturaVentaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/factura-ventas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
