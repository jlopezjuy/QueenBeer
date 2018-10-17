package com.anelsoftware.beer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anelsoftware.beer.service.ProductoService;
import com.anelsoftware.beer.web.rest.errors.BadRequestAlertException;
import com.anelsoftware.beer.web.rest.util.HeaderUtil;
import com.anelsoftware.beer.service.dto.ProductoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Producto.
 */
@RestController
@RequestMapping("/api")
public class ProductoResource {

    private final Logger log = LoggerFactory.getLogger(ProductoResource.class);

    private static final String ENTITY_NAME = "producto";

    private ProductoService productoService;

    public ProductoResource(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * POST  /productos : Create a new producto.
     *
     * @param productoDTO the productoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productoDTO, or with status 400 (Bad Request) if the producto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/productos")
    @Timed
    public ResponseEntity<ProductoDTO> createProducto(@RequestBody ProductoDTO productoDTO) throws URISyntaxException {
        log.debug("REST request to save Producto : {}", productoDTO);
        if (productoDTO.getId() != null) {
            throw new BadRequestAlertException("A new producto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoDTO result = productoService.save(productoDTO);
        return ResponseEntity.created(new URI("/api/productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /productos : Updates an existing producto.
     *
     * @param productoDTO the productoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productoDTO,
     * or with status 400 (Bad Request) if the productoDTO is not valid,
     * or with status 500 (Internal Server Error) if the productoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/productos")
    @Timed
    public ResponseEntity<ProductoDTO> updateProducto(@RequestBody ProductoDTO productoDTO) throws URISyntaxException {
        log.debug("REST request to update Producto : {}", productoDTO);
        if (productoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductoDTO result = productoService.save(productoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /productos : get all the productos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of productos in body
     */
    @GetMapping("/productos")
    @Timed
    public List<ProductoDTO> getAllProductos() {
        log.debug("REST request to get all Productos");
        return productoService.findAll();
    }

    /**
     * GET  /productos/:id : get the "id" producto.
     *
     * @param id the id of the productoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/productos/{id}")
    @Timed
    public ResponseEntity<ProductoDTO> getProducto(@PathVariable Long id) {
        log.debug("REST request to get Producto : {}", id);
        Optional<ProductoDTO> productoDTO = productoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoDTO);
    }

    /**
     * DELETE  /productos/:id : delete the "id" producto.
     *
     * @param id the id of the productoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/productos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        log.debug("REST request to delete Producto : {}", id);
        productoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/productos?query=:query : search for the producto corresponding
     * to the query.
     *
     * @param query the query of the producto search
     * @return the result of the search
     */
    @GetMapping("/_search/productos")
    @Timed
    public List<ProductoDTO> searchProductos(@RequestParam String query) {
        log.debug("REST request to search Productos for query {}", query);
        return productoService.search(query);
    }

}
