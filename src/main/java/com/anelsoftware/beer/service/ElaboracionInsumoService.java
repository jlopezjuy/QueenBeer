package com.anelsoftware.beer.service;

import com.anelsoftware.beer.service.dto.ElaboracionInsumoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ElaboracionInsumo.
 */
public interface ElaboracionInsumoService {

    /**
     * Save a elaboracionInsumo.
     *
     * @param elaboracionInsumoDTO the entity to save
     * @return the persisted entity
     */
    ElaboracionInsumoDTO save(ElaboracionInsumoDTO elaboracionInsumoDTO);

    /**
     * Get all the elaboracionInsumos.
     *
     * @return the list of entities
     */
    List<ElaboracionInsumoDTO> findAll();


    /**
     * Get the "id" elaboracionInsumo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ElaboracionInsumoDTO> findOne(Long id);

    /**
     * Delete the "id" elaboracionInsumo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the elaboracionInsumo corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ElaboracionInsumoDTO> search(String query);
}
