package com.anelsoftware.beer.service;

import com.anelsoftware.beer.service.dto.ProveedorDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Proveedor.
 */
public interface ProveedorService {

    /**
     * Save a proveedor.
     *
     * @param proveedorDTO the entity to save
     * @return the persisted entity
     */
    ProveedorDTO save(ProveedorDTO proveedorDTO);

    /**
     * Get all the proveedors.
     *
     * @return the list of entities
     */
    List<ProveedorDTO> findAll();


    /**
     * Get the "id" proveedor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ProveedorDTO> findOne(Long id);

    /**
     * Delete the "id" proveedor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the proveedor corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ProveedorDTO> search(String query);
}
