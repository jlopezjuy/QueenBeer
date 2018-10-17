package com.anelsoftware.beer.service;

import com.anelsoftware.beer.service.dto.InsumoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Insumo.
 */
public interface InsumoService {

    /**
     * Save a insumo.
     *
     * @param insumoDTO the entity to save
     * @return the persisted entity
     */
    InsumoDTO save(InsumoDTO insumoDTO);

    /**
     * Get all the insumos.
     *
     * @return the list of entities
     */
    List<InsumoDTO> findAll();


    /**
     * Get the "id" insumo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<InsumoDTO> findOne(Long id);

    /**
     * Delete the "id" insumo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the insumo corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<InsumoDTO> search(String query);
}
