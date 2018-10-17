package com.anelsoftware.beer.service;

import com.anelsoftware.beer.service.dto.ListaPrecioDTO;

import java.util.List;
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
     * @return the list of entities
     */
    List<ListaPrecioDTO> findAll();


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
     * @return the list of entities
     */
    List<ListaPrecioDTO> search(String query);
}
