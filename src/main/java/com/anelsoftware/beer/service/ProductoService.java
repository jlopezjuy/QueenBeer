package com.anelsoftware.beer.service;

import com.anelsoftware.beer.service.dto.ProductoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Producto.
 */
public interface ProductoService {

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save
     * @return the persisted entity
     */
    ProductoDTO save(ProductoDTO productoDTO);

    /**
     * Get all the productos.
     *
     * @return the list of entities
     */
    List<ProductoDTO> findAll();


    /**
     * Get the "id" producto.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProductoDTO> findOne(Long id);

    /**
     * Delete the "id" producto.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the producto corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ProductoDTO> search(String query);
}
