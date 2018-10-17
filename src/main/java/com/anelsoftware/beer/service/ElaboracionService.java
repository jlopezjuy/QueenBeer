package com.anelsoftware.beer.service;

import com.anelsoftware.beer.service.dto.ElaboracionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ElaboracionDTO> findAll(Pageable pageable);


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
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ElaboracionDTO> search(String query, Pageable pageable);
}
