package com.anelsoftware.beer.service;

import com.anelsoftware.beer.service.dto.ProductoEnvaseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ProductoEnvase.
 */
public interface ProductoEnvaseService {

    /**
     * Save a productoEnvase.
     *
     * @param productoEnvaseDTO the entity to save
     * @return the persisted entity
     */
    ProductoEnvaseDTO save(ProductoEnvaseDTO productoEnvaseDTO);

    /**
     * Get all the productoEnvases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductoEnvaseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" productoEnvase.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductoEnvaseDTO> findOne(Long id);

    /**
     * Delete the "id" productoEnvase.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the productoEnvase corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProductoEnvaseDTO> search(String query, Pageable pageable);
}
