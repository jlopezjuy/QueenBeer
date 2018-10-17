package com.anelsoftware.beer.service;

import com.anelsoftware.beer.service.dto.ElaboracionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Elaboracion.
 */
public interface ElaboracionService {

    /**
     * Save a elaboracion.
     *
     * @param elaboracionDTO the entity to save
     * @return the persisted entity
     */
    ElaboracionDTO save(ElaboracionDTO elaboracionDTO);

    /**
     * Get all the elaboracions.
     *
     * @return the list of entities
     */
    List<ElaboracionDTO> findAll();


    /**
     * Get the "id" elaboracion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ElaboracionDTO> findOne(Long id);

    /**
     * Delete the "id" elaboracion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the elaboracion corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ElaboracionDTO> search(String query);
}
