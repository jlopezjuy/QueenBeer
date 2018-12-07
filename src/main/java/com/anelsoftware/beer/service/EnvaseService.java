package com.anelsoftware.beer.service;

import com.anelsoftware.beer.service.dto.EnvaseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Envase.
 */
public interface EnvaseService {

    /**
     * Save a envase.
     *
     * @param envaseDTO the entity to save
     * @return the persisted entity
     */
    EnvaseDTO save(EnvaseDTO envaseDTO);

    /**
     * Get all the envases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EnvaseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" envase.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EnvaseDTO> findOne(Long id);

    /**
     * Delete the "id" envase.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the envase corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EnvaseDTO> search(String query, Pageable pageable);
}
