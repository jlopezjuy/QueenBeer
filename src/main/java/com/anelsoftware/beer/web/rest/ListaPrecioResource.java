package com.anelsoftware.beer.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.anelsoftware.beer.service.ListaPrecioService;
import com.anelsoftware.beer.web.rest.errors.BadRequestAlertException;
import com.anelsoftware.beer.web.rest.util.HeaderUtil;
import com.anelsoftware.beer.web.rest.util.PaginationUtil;
import com.anelsoftware.beer.service.dto.ListaPrecioDTO;
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
 * REST controller for managing ListaPrecio.
 */
@RestController
@RequestMapping("/api")
public class ListaPrecioResource {

    private final Logger log = LoggerFactory.getLogger(ListaPrecioResource.class);

    private static final String ENTITY_NAME = "listaPrecio";

    private ListaPrecioService listaPrecioService;

    public ListaPrecioResource(ListaPrecioService listaPrecioService) {
        this.listaPrecioService = listaPrecioService;
    }

    /**
     * POST  /lista-precios : Create a new listaPrecio.
     *
     * @param listaPrecioDTO the listaPrecioDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new listaPrecioDTO, or with status 400 (Bad Request) if the listaPrecio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lista-precios")
    @Timed
    public ResponseEntity<ListaPrecioDTO> createListaPrecio(@RequestBody ListaPrecioDTO listaPrecioDTO) throws URISyntaxException {
        log.debug("REST request to save ListaPrecio : {}", listaPrecioDTO);
        if (listaPrecioDTO.getId() != null) {
            throw new BadRequestAlertException("A new listaPrecio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListaPrecioDTO result = listaPrecioService.save(listaPrecioDTO);
        return ResponseEntity.created(new URI("/api/lista-precios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lista-precios : Updates an existing listaPrecio.
     *
     * @param listaPrecioDTO the listaPrecioDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated listaPrecioDTO,
     * or with status 400 (Bad Request) if the listaPrecioDTO is not valid,
     * or with status 500 (Internal Server Error) if the listaPrecioDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lista-precios")
    @Timed
    public ResponseEntity<ListaPrecioDTO> updateListaPrecio(@RequestBody ListaPrecioDTO listaPrecioDTO) throws URISyntaxException {
        log.debug("REST request to update ListaPrecio : {}", listaPrecioDTO);
        if (listaPrecioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListaPrecioDTO result = listaPrecioService.save(listaPrecioDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, listaPrecioDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lista-precios : get all the listaPrecios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of listaPrecios in body
     */
    @GetMapping("/lista-precios")
    @Timed
    public ResponseEntity<List<ListaPrecioDTO>> getAllListaPrecios(Pageable pageable) {
        log.debug("REST request to get a page of ListaPrecios");
        Page<ListaPrecioDTO> page = listaPrecioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lista-precios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /lista-precios/:id : get the "id" listaPrecio.
     *
     * @param id the id of the listaPrecioDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the listaPrecioDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lista-precios/{id}")
    @Timed
    public ResponseEntity<ListaPrecioDTO> getListaPrecio(@PathVariable Long id) {
        log.debug("REST request to get ListaPrecio : {}", id);
        Optional<ListaPrecioDTO> listaPrecioDTO = listaPrecioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listaPrecioDTO);
    }

    /**
     * DELETE  /lista-precios/:id : delete the "id" listaPrecio.
     *
     * @param id the id of the listaPrecioDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lista-precios/{id}")
    @Timed
    public ResponseEntity<Void> deleteListaPrecio(@PathVariable Long id) {
        log.debug("REST request to delete ListaPrecio : {}", id);
        listaPrecioService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/lista-precios?query=:query : search for the listaPrecio corresponding
     * to the query.
     *
     * @param query the query of the listaPrecio search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/lista-precios")
    @Timed
    public ResponseEntity<List<ListaPrecioDTO>> searchListaPrecios(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ListaPrecios for query {}", query);
        Page<ListaPrecioDTO> page = listaPrecioService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/lista-precios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
