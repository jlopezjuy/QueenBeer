package com.anelsoftware.beer.service;

import com.anelsoftware.beer.service.dto.ListaPrecioDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ListaPrecio.
 */
public interface ListaPrecioService {

    /**
     * Save a listaPrecio.
     *
     * @param listaPrecioDTO the entity to save
     * @return the persisted entity
     */
    ListaPrecioDTO save(ListaPrecioDTO listaPrecioDTO);

    /**
     * Get all the listaPrecios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ListaPrecioDTO> findAll(Pageable pageable);


    /**
     * Get the "id" listaPrecio.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ListaPrecioDTO> findOne(Long id);

    /**
     * Delete the "id" listaPrecio.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the listaPrecio corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ListaPrecioDTO> search(String query, Pageable pageable);
}
